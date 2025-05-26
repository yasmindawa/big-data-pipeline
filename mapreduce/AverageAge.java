import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AverageAge {

    public static class AgeMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static Text outputKey = new Text("age");
        private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split(",");
            if (fields.length == 2) {
                try {
                    Date dob = sdf.parse(fields[1]);
                    Date referenceDate = sdf.parse("2200-01-01 00:00:00");
                    long ageInMillis = referenceDate.getTime() - dob.getTime();
                    int age = (int)(ageInMillis / (1000L * 60 * 60 * 24 * 365));
                    context.write(outputKey, new IntWritable(age));
                } catch (Exception e) {
                    // Skip rows with invalid dob
                }
            }
        }
    }

    public static class AvgReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            int count = 0;
            for (IntWritable val : values) {
                sum += val.get();
                count++;
            }
            if (count > 0) {
                double average = (double) sum / count;
                context.write(new Text("Average Age"), new DoubleWritable(average));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Average Age Calculator");
        job.setJarByClass(AverageAge.class);
        job.setMapperClass(AgeMapper.class);
        job.setReducerClass(AvgReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}