# 🛠️ Aplikasi Service Bengkel 2024

## 📚 Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Team](#team)
- [Contributing](#contributing)
- [License](#license)

## 🧾 Introduction

**Aplikasi Service Bengkel 2024** adalah sistem manajemen bengkel berbasis web yang dibangun menggunakan **Spring Boot**. Aplikasi ini membantu mengelola operasional bengkel motor dengan efisien, termasuk manajemen mekanik, transaksi servis pelanggan, dan pengelolaan stok sparepart. Terdapat dua jenis pengguna utama: **Admin** dan **Mekanik**. Admin bertugas menambahkan mekanik, mencatat kedatangan pelanggan, dan menyiapkan stok sparepart. Mekanik bertanggung jawab atas pengerjaan servis dan pencatatan sparepart yang digunakan saat pengerjaan.

Aplikasi ini mempermudah alur kerja bengkel dari sisi operasional, mulai dari admin menugaskan pelanggan ke mekanik hingga mekanik menyelesaikan servis dan mencatat transaksi. Data akan otomatis tersimpan dan dapat dikelola melalui dashboard berbasis web.

## ✨ Features

- Login berdasarkan role (Admin & Mekanik)
- Admin dapat menambahkan mekanik dan sparepart
- Mekanik melihat servis yang ditugaskan kepadanya
- Mekanik mencatat sparepart yang digunakan
- Manajemen transaksi servis
- Sistem database otomatis di-*seed* saat pertama kali dijalankan

## ⚙️ Installation

### 🧩 Prerequisites
Pastikan kamu sudah menginstall:

- [Java JDK 17+](https://jdk.java.net/)
- [Maven](https://maven.apache.org/)
- [MySQL Server](https://www.mysql.com/)
- [Spring Boot CLI (optional)](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-installing)

### 📦 Setup Step-by-Step

1. **Clone this repository**
   ```bash
   git clone https://github.com/PBO-Kelompok-1/SpringCore.git
   cd SpringCore

2. **Create a new MySQL database**
   ```bash
   CREATE DATABASE bengkeljawa;

3. **Sesuaikan konfigurasi database**
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/bengkeljawa
   spring.datasource.username=root
   spring.datasource.password=

4. 3. **Sesuaikan konfigurasi database**
   ```bash
   mvn clean install
   mvn spring-boot:run
