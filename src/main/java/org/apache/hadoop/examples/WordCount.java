package org.apache.hadoop.examples;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.log4j.Logger;

public class WordCount {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: wordcount <in> <out>");
			System.exit(2);
		}

		Job job = new Job(conf, "word count");
		job.setJarByClass(WordCount.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	public static class IntSumReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		static int cnt = 0;
		private IntWritable result = new IntWritable();
		Logger log = Logger.getLogger(this.getClass());

		public void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {

			log.info("reduce call count : " + (++cnt));

			int sum = 0;
			for (IntWritable val : values) {
				// log.info(val.get());
				sum += val.get();
			}
			this.result.set(sum);
			context.write(key, this.result);
		}
	}

	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, IntWritable> {
		private static final IntWritable one = new IntWritable(1);
		private Text word = new Text();
		static int cnt = 0;
		Logger log = Logger.getLogger(this.getClass());

		public void map(Object key, Text value,
				Mapper<Object, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			
			log.info("map call count : " + (++cnt));
			
			StringTokenizer itr = new StringTokenizer(value.toString());
			// log.info(value.toString());
			while (itr.hasMoreTokens()) {
				this.word.set(itr.nextToken());
				context.write(this.word, one);
			}
			
		}
		
		public void cleanup(Context context) {
			try {
				log.info("================================= context info =======================================");
				log.info("getJar:"+context.getJar());
				log.info("getNumReduceTasks:"+context.getNumReduceTasks());
				log.info("getJobName:"+context.getJobName());
				log.info("toString :"+context.toString());
				log.info("getCurrentValue : "+context.getCurrentValue());
			} catch (Exception e) {
				log.error(e,e);
			}
		}
	}
}