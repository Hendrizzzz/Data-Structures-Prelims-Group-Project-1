# Sorting Algorithms Benchmarking 
**Note**: This README file is provided before the testing phase. Details on benchmarking results and performance comparisons will be added after the testing is completed.

**Note**: If you encounter issues with dataset files, download the files directly from the repository and replace the existing ones in your local project directory.

## Overview

This repository contains implementations and benchmarks for three sorting algorithms: **Bubble Sort**, **Insertion Sort**, and **Selection Sort**. The purpose of this project is to compare these algorithms based on the number of statements executed during the sorting process across different data sizes and orderings.

## Project Objective

The general objective of this project is to evaluate and compare the Bubble Sort, Insertion Sort, and Selection Sort algorithms based on their statement execution count. The comparison involves sorting datasets of varying sizes and orders, with the goal of presenting a detailed analysis of the algorithms' performance.

## Data Preparation

The project includes five sets of data, each in three variants:

- **Data Sizes**:
  - 10,000 records
  - 50,000 records
  - 200,000 records
  - 500,000 records
  - 1,000,000 records

- **Variants**:
  - **Ascending Order**: Data sorted in ascending order.
  - **Descending Order**: Data sorted in descending order.
  - **Random Order**: Data in random order.

### Datasets

- **`medical_records_dataset_ascending.csv`**: Contains medical records sorted in ascending order.
- **`medical_records_dataset_descending.csv`**: Contains medical records sorted in descending order.
- **`medical_records_dataset_random.csv`**: Contains medical records in random order.

**Note**: To use these datasets, ensure Git LFS is installed. Fetch the large files using `git lfs pull`.

## Git LFS Usage

Git Large File Storage (Git LFS) is a Git extension designed to handle large files in a repository more efficiently. Standard Git is not well-suited for versioning large files, as it stores every version of every file in your repository's history. This can quickly bloat the size of your repository, making it difficult to manage and share.

In this project, the dataset files can be very large, particularly when working with up to 1,000,000 records. These files can exceed the standard storage limits of Git, especially on platforms like GitLab, **which has a default file size limit of 100MB per file.** To accommodate these large files without exceeding storage limits, we use Git LFS.

## Usage

To work with these datasets, follow these steps:

1. **Install Git LFS**: Ensure Git LFS is installed and configured on your system.
2. **Fetch LFS files**: Run `git lfs pull` to download the LFS-tracked files if you encounter errors related to missing files.

Otherwise, if Git LFS is not available, follow these steps:

1. **Download Data Set Manually**: Download the three required data set inside the src/dataset/ directory. This grabs the actual file outside the Git LFS Server.
2. **Place them inside the repository**: Place them inside your cloned repository to the same src/dataset/ directory.

## Algorithm Implementations

The repository includes Java implementations for the following sorting algorithms:

- **Bubble Sort**
- **Insertion Sort**
- **Selection Sort**

Each implementation is modified to count and report the number of statements executed during sorting.

## Developers

- **ANGELO, Franz Carlo** 
- **BAG-EO, Jim Hendrix** 
- **BERNABE, Hyowon Arzil**  
- **CARANI, Joeffrey Edrian** 
- **CARDENAS, Aaron** 
- **MATULAY, Audrey** 
- **SAIPEN, Xylon** 