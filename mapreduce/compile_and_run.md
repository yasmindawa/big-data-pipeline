## Setup and Execution – MapReduce Job (Inside Namenode)

```bash
# Create folders
mkdir -p ~/avg-age-job/src ~/avg-age-job/build

# Copy Java code into the container
docker cp ./mapreduce/AverageAge.java namenode:/root/avg-age-job/src/AverageAge.java

# Compile the code
javac -classpath `hadoop classpath` -d ~/avg-age-job/build ~/avg-age-job/src/AverageAge.java

# Create the JAR
jar -cvf ~/avg-age-job/average-age.jar -C ~/avg-age-job/build/ .

# Check HDFS for the correct input path
hdfs dfs -ls /user/hive/warehouse/patients_for_mr/patients_for_mr

# Run the MapReduce job
hadoop jar ~/avg-age-job/average-age.jar AverageAge /user/hive/warehouse/patients_for_mr/patients_for_mr /user/hive/warehouse/average_age_output

# View output
hdfs dfs -cat /user/hive/warehouse/average_age_output/part-r-00000