
package org.apache.gora.project.log;

import java.io.IOException;

import org.apache.avro.util.Utf8;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.gora.mapreduce.GoraMapper;
import org.apache.gora.mapreduce.GoraReducer;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.gora.project.log.generated.MetricDatum;
import org.apache.gora.project.log.generated.Pageview;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*This has been referred from Apacge Gora Tutorial . The same example has been implemented with some minor changes */


public class LogAnalytics extends Configured implements Tool {

  private static final Logger log = LoggerFactory.getLogger(LogAnalytics.class);
  

  private static final long DAY_TIME = 1000 * 60 * 60 * 24;
    
 
  public static class LogAnalyticsMapper extends GoraMapper<Long, Pageview, TextLong,
      LongWritable> {
    
    private LongWritable one = new LongWritable(1L);
  
    private TextLong tuplerow;
    
    @Override
    protected void setup(Context context) throws IOException ,InterruptedException {
      tuplerow = new TextLong();
      tuplerow.setKey(new Text());
      tuplerow.setValue(new LongWritable());
    }

    @Override
    protected void map(Long key, Pageview pageview, Context context)
        throws IOException ,InterruptedException {
      
      CharSequence url = pageview.getUrl();
      long day = getDay(pageview.getTimestamp());
      
      tuplerow.getKey().set(url.toString());
      tuplerow.getValue().set(day);
      
      context.write(tuplerow, one);
    }

    
    private long getDay(long timeStamp) {
      return (timeStamp / DAY_TIME) * DAY_TIME; 
    } //no of milliseconds in a day
  }
  

  public static class LogAnalyticsReducer extends GoraReducer<TextLong, LongWritable,
      String, MetricDatum> {
    
    private MetricDatum metricDatum = new MetricDatum();
    
    @Override
    protected void reduce(TextLong tuplerow, Iterable<LongWritable> values, Context context)
      throws IOException ,InterruptedException {
      
      long sum = 0L; //sum up the values
      for(LongWritable value: values) {
        sum+= value.get();
      }
      
      String dimension = tuplerow.getKey().toString();
      long timestamp = tuplerow.getValue().get();
      
      metricDatum.setMetricDimension(new Utf8(dimension));
      metricDatum.setTimestamp(timestamp);
      
      String key = metricDatum.getMetricDimension().toString();
      key += "_" + Long.toString(timestamp);
      metricDatum.setMetric(sum);
      
      context.write(key, metricDatum);
    }
  }
  
 
  public Job createJob(DataStore<Long, Pageview> inStore,
      DataStore<String, MetricDatum> outStore, int numReducer) throws IOException {
    Job job = new Job(getConf());
    job.setJobName("Log Analytics");
    log.info("Creating Gora Hadoop Job: {}", job.getJobName());
    job.setNumReduceTasks(numReducer);
    job.setJarByClass(getClass());

   /*Using Gora datastore as inpout and outptu */
    GoraMapper.initMapperJob(job, inStore, TextLong.class, LongWritable.class,
        LogAnalyticsMapper.class, true);

    
    GoraReducer.initReducerJob(job, outStore, LogAnalyticsReducer.class);
    
    return job;
  }
  
  @Override
  public int run(String[] args) throws Exception {
    
    DataStore<Long, Pageview> inStore;
    DataStore<String, MetricDatum> outStore;
    Configuration conf = new Configuration();

    if(args.length > 0) {
      String dataStoreClass = args[0];
      inStore = DataStoreFactory.
          getDataStore(dataStoreClass, Long.class, Pageview.class, conf);
      if(args.length > 1) {
        dataStoreClass = args[1];
      }
      outStore = DataStoreFactory.
          getDataStore(dataStoreClass, String.class, MetricDatum.class, conf);
    } else {
	    inStore = DataStoreFactory.getDataStore(Long.class, Pageview.class, conf);
	    outStore = DataStoreFactory.getDataStore(String.class, MetricDatum.class, conf);
    }
    
    Job job = createJob(inStore, outStore, 3);
    boolean success = job.waitForCompletion(true);
    
    inStore.close();
    outStore.close();
    
    log.info("Log completed with {}", (success ? "success" : "failure"));
    
    return success ? 0 : 1;
  }
  
  private static final String USAGE = "LogAnalytics <input_data_store> <output_data_store>";
  
  public static void main(String[] args) throws Exception {
    if(args.length < 2) {
      log.info(USAGE);
      System.exit(1);
    }
    //run as any other MR job
    int ret = ToolRunner.run(new LogAnalytics(), args);
    System.exit(ret);
  }
  
}
