package com.socurites.example.hadoop.wordcount.driver;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class TestDriver extends Configured implements Tool{

	@Override
	public int run(String[] arg0) throws Exception {
		Job job = new Job(this.getConf(), "Word Count");
		job.setJarByClass(this.getClass());
		job.setMapperClass(TestMapper.class);
		job.waitForCompletion(true);
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new TestDriver(), args);
		System.exit(res);
	}

	public static class TestMapper extends Mapper<LongWritable, Text, SecondarySort.IntPair, IntWritable>
	{
		public void map(LongWritable inKey, Text inValue, Mapper<LongWritable, Text, SecondarySort.IntPair, IntWritable>.Context context)
				throws IOException, InterruptedException
				{
			System.out.println("woijwf");
				}
	} 
}
