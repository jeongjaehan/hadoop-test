package com.socurites.example.hadoop.wordcount.driver;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.socurites.example.hadoop.wordcount.mapper.WordCountMapper;
import com.socurites.example.hadoop.wordcount.reducer.WordCountReducer;

public class WordCountDriver extends Configured implements Tool {
	public int run(String[] args) throws Exception {
		// Job생성하기
		Job job = new Job(this.getConf(), "Word Count");
		job.setJarByClass(this.getClass());
				
		// Mapper 설정하기
		job.setMapperClass(WordCountMapper.class);
		// Reducer 설정하기
		job.setReducerClass(WordCountReducer.class);
		
		// 입력 경로 설정하기
		FileInputFormat.addInputPath(job, new Path(args[0]));
		// 출력 경로 설정하기
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		// 입력 포맷 설정하기
		job.setInputFormatClass(TextInputFormat.class);
		
		// 출력 포맷 설정하기
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.waitForCompletion(true);
		
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = 0;
		if ( hasEnoughArguments(args) ) {
			System.err.println("사용법: WordCountDriver [generic options] <input> <output>");
			ToolRunner.printGenericCommandUsage(System.err);
			exitCode = -1;
		} else {
			exitCode = ToolRunner.run(new WordCountDriver(), args);
		}
		
		System.exit(exitCode);
	}
	
	private static boolean hasEnoughArguments(String[] consoleArgs) {
		if ( consoleArgs.length == 2 ) {
			return true;
		}
		
		return false;
	}

}
