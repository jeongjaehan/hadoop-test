package com.socurites.example.hadoop.wordcount.mapper;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.junit.Test;

import com.socurites.example.hadoop.wordcount.mapper.WordCountMapper;

public class WordCountMapperTest {
	@Test
	public void processValidRecord() throws IOException, InterruptedException {
		WordCountMapper mapper = new WordCountMapper();
		
		LongWritable key = null;
		Text value = new Text("2005년 12월 8일 야후! 코리아의 제안으로 Yahoo! Answers 서비스 개시");
		Context context = mock(Context.class);
		
		mapper.map(key, value, context);
		
		verify(context, times(1)).write(new Text("2005년"), new IntWritable(1));
		verify(context, times(1)).write(new Text("Yahoo!"), new IntWritable(1));		
	}
}
