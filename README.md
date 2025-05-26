[![Gitter chat](https://badges.gitter.im/gitterHQ/gitter.png)](https://gitter.im/big-data-europe/Lobby)

# Big Data Pipeline for MIMIC-III Clinical Data

This project sets up a basic big data pipeline using Docker, Hadoop, Hive, and Hue to store and query cleaned MIMIC-III clinical data in Parquet format. It also includes a MapReduce job written in Java for batch analytics.

# Project Architecture:

big_data_project/
│
├── data/
│   ├── parquet/            #Parquet files used in HDFS
│   │   ├── admissions.parquet
│   │   ├── diagnosis_full.parquet
│   │   ├── icustays.parquet
│   │   └── patients.parquet
│   ├── raw/                 # Original CSV files
│   │   ├── admissions.csv
│   │   ├── d_icd_diagnoses.csv
│   │   ├── diagnoses_icd.csv
│   │   ├── icustays.csv
│   │   └── patients.csv
│   ├── mysql/
│   ├── hue/
│   └── init.sql
│
├── notebooks/
│   └── clean_and_convert.ipynb  # Data cleaning + Parquet export
│
├── mapreduce/
│   ├── AverageAge.java      # Java code for MapReduce job
│   ├── average-age.jar      # Compiled JAR
│   ├── output_example.txt   # Sample output
│   ├── compile_and_run.md   # How to compile and run the job
│   └── README.md
│
├── docker-compose.yml
├── startup.sh
├── entrypoint.sh
├── hadoop.env
├── hadoop-hive.env
│
├── docs/
│   └── Big Data Project Manual.pdf
│
├── README.md
└── .gitignore


# Tools & Technologies

- Docker & Docker Compose

- Hadoop (HDFS, YARN)

- Hive + Hive Metastore (PostgreSQL or MySQL)

- Hue (Web UI for querying Hive)

- Java (MapReduce)


 # Pipeline Overview

Converted CSV files to Parquet using (see clean_and_convert.ipynb)

Used Docker Compose to spin up Hadoop + Hive services

Copied Parquet files into HDFS

Created Hive external tables

Queried using Hive CLI or Hue Web UI

Compiled and ran a MapReduce job to calculate average patient age

# Data & Source Links

Based on: https://github.com/Marcel-Jan/docker-hadoop-spark

MIMIC-III Dataset: https://mimic.physionet.org/
