package com.socurites.example.hadoop.wordcount.reducer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.util.Arrays;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.junit.Test;


public class WordCountReducerTest {

	@Test
	public void processValidRecord() throws IOException, InterruptedException {
		WordCountReducer reducer = new WordCountReducer();
		
		Text key = new Text("2005년");
		Iterable<IntWritable> values = Arrays.asList(new IntWritable(1), new IntWritable(1));
		Context context = mock(Context.class);
		
		reducer.reduce(key, values, context);
		
		verify(context).write(key, new IntWritable(2));
	}

}
