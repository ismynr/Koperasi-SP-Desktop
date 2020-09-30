-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 11, 2019 at 08:40 AM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `phbjava2_koperasi`
--

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `id` char(6) NOT NULL,
  `nama` varchar(40) DEFAULT NULL,
  `nik` varchar(50) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `jk` enum('P','L') DEFAULT NULL,
  `pekerjaan` int(11) DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `alamat` text,
  `email` varchar(70) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`id`, `nama`, `nik`, `tgl_lahir`, `jk`, `pekerjaan`, `no_hp`, `alamat`, `email`, `created_at`, `updated_at`) VALUES
('AG0002', 'Saevia IZ', 'MzMyODEyNTEwNzk5MDAwMg==', '2019-06-13', 'P', 1, 'MDkzNDU4NzI3OA==', 'dGVnYWx3YW5naQ==', 'c2FldmlhQGdtYWlsLmNvbQ==', '2019-07-05 13:16:28', '2019-07-05 13:16:28'),
('AG0003', 'uzumaki', 'MzMyODEyNTEwNzk5MDAwMw==', '2019-06-03', 'P', 4, 'MDg5ODcxNTk4ODU=', 'a29ub2hhCQ==', 'dXp1bWFraUB5YWhvby5jb20=', '2019-06-28 11:28:38', '2019-06-28 11:28:38'),
('AG0004', 'Ismi Nururrizqi', 'MzMyODEyNTEwNzk5MDAwMQ==', '1999-07-11', 'P', 5, 'MDg5ODcxNTk4ODQ=', 'dGVnYWx3YW5naSB0YWxhbmcgdGVnYWwJ', 'aXNtaUBnbWFpbC5jb20=', '2019-07-04 02:56:06', '2019-07-04 02:56:06'),
('AG0005', 'Fina Maghfiroh', 'MzMyODEyNTEwNzk5MDAwNA==', '2019-06-19', 'P', 4, 'MDg4NDczODIzNDk=', 'cHVsb3NhcmkJ', 'ZmluYUBnbWFpbC5jb20=', '2019-06-29 12:06:06', NULL),
('AG0006', 'Tri Fitriana Putri', 'ODczNDc1ODM2Mzg3MjY0', '2019-07-04', 'P', 1, 'MDIzODQ3MjM0Njg3', 'a2FsaW11a3RpCQ==', 'cHV0cmlAZ21haWwuY29t', '2019-07-04 02:31:58', NULL),
('AG0007', 'Saadah saadah ', 'MTg3NDg5NTY3MjY=', '2019-07-10', 'P', 1, 'ODc0Mjk4NzI1NA==', 'Y2lyZWJvbg==', 'c2FhZGFoQGdtYWlsLmNvbQ==', '2019-07-11 04:10:36', '2019-07-11 04:10:36'),
('AG0008', 'Istiqomah', 'ODE3MzU5NDc2OTI3NDQ4', '2019-07-25', 'P', 4, 'MDgyNDc4NTc2Mjc0', 'dGVnYWwgdGFsYW5n', 'aXN0aUBnbWFpbC5jb20=', '2019-07-04 02:33:17', NULL),
('AG0009', 'KOPERASI NR', 'MTExMTExMTExMTExMTEx', '2019-01-01', 'P', 6, 'MTExMTExMTExMTEx', 'S09QRVJBU0k=', 'a29wZXJhc2lAZ21haWwuY29t', '2019-07-04 03:27:46', '2019-07-04 03:27:46');

-- --------------------------------------------------------

--
-- Table structure for table `angsuran_pinjaman`
--

CREATE TABLE `angsuran_pinjaman` (
  `id` int(11) NOT NULL,
  `id_pinjam` varchar(8) DEFAULT NULL,
  `nama_angg` varchar(40) NOT NULL,
  `angsur_ke` int(11) DEFAULT NULL,
  `jml_angsur` int(11) DEFAULT NULL,
  `sisa_angsur` int(11) DEFAULT NULL,
  `tgl_angsur` date DEFAULT NULL,
  `denda` int(11) NOT NULL,
  `tgl_tempo` date NOT NULL,
  `status_angsur` enum('On','Off') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `angsuran_pinjaman`
--

INSERT INTO `angsuran_pinjaman` (`id`, `id_pinjam`, `nama_angg`, `angsur_ke`, `jml_angsur`, `sisa_angsur`, `tgl_angsur`, `denda`, `tgl_tempo`, `status_angsur`) VALUES
(1, 'PJM00001', 'Saevia IZ', 1, 17000, 34000, '2019-02-01', 0, '2019-02-05', 'On');

-- --------------------------------------------------------

--
-- Table structure for table `jenis_simpanan`
--

CREATE TABLE `jenis_simpanan` (
  `id` varchar(4) NOT NULL,
  `jenis_simpanan` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jenis_simpanan`
--

INSERT INTO `jenis_simpanan` (`id`, `jenis_simpanan`) VALUES
('JS1', 'Simpanan Pokok'),
('JS2', 'Simpanan Wajib'),
('JS3', 'Simpanan Sukarela'),
('JS4', 'Donasi'),
('JS5', 'Denda Pinjaman');

-- --------------------------------------------------------

--
-- Table structure for table `pekerjaan`
--

CREATE TABLE `pekerjaan` (
  `id` int(11) NOT NULL,
  `pekerjaan` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pekerjaan`
--

INSERT INTO `pekerjaan` (`id`, `pekerjaan`) VALUES
(1, 'Pelajar'),
(2, 'Buruh'),
(3, 'Guru Swasta'),
(4, 'PNS'),
(5, 'Engineer'),
(6, 'Karyawan');

-- --------------------------------------------------------

--
-- Table structure for table `pengeluaran`
--

CREATE TABLE `pengeluaran` (
  `id` varchar(8) NOT NULL,
  `jml_pengl` varchar(11) DEFAULT NULL,
  `tgl_pengl` date DEFAULT NULL,
  `ket` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pengeluaran`
--

INSERT INTO `pengeluaran` (`id`, `jml_pengl`, `tgl_pengl`, `ket`) VALUES
('PNG00001', '700000', '2019-01-05', 'beli alat2 koperasi'),
('PNG00002', '500000', '2019-01-04', 'keperluan rapat'),
('PNG00003', '100000', '2019-07-01', 'rapat'),
('PNG00004', '50000', '2019-07-05', 'beli sapu 10');

-- --------------------------------------------------------

--
-- Table structure for table `pinjaman`
--

CREATE TABLE `pinjaman` (
  `id` varchar(8) NOT NULL,
  `id_angg` varchar(6) DEFAULT NULL,
  `nama_angg` varchar(40) NOT NULL,
  `jml_pinjam` int(11) DEFAULT NULL,
  `tenor` varchar(4) DEFAULT NULL,
  `bunga` float DEFAULT NULL,
  `angsuran` int(11) DEFAULT NULL,
  `jml_keseluruhan` int(11) NOT NULL,
  `tgl_pinjam` date DEFAULT NULL,
  `status_pinjam` enum('Lunas','Berjalan') DEFAULT NULL,
  `ket` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pinjaman`
--

INSERT INTO `pinjaman` (`id`, `id_angg`, `nama_angg`, `jml_pinjam`, `tenor`, `bunga`, `angsuran`, `jml_keseluruhan`, `tgl_pinjam`, `status_pinjam`, `ket`) VALUES
('PJM00001', 'AG0002', 'Saevia IZ', 50000, 'TR3', 2, 17000, 51000, '2019-01-05', 'Berjalan', 'untuk makan sementara ini');

-- --------------------------------------------------------

--
-- Table structure for table `simpanan`
--

CREATE TABLE `simpanan` (
  `id` varchar(8) NOT NULL,
  `id_angg` varchar(6) DEFAULT NULL,
  `nama_angg` varchar(40) NOT NULL,
  `jml_simpan` int(11) DEFAULT NULL,
  `jns_simpan` varchar(4) DEFAULT NULL,
  `tgl_simpan` date DEFAULT NULL,
  `ket` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `simpanan`
--

INSERT INTO `simpanan` (`id`, `id_angg`, `nama_angg`, `jml_simpan`, `jns_simpan`, `tgl_simpan`, `ket`) VALUES
('SMP00001', 'AG0008', 'Istiqomah', 50000, 'JS1', '2019-01-01', 'pendaftar pertama'),
('SMP00002', 'AG0007', 'Saadah saadah ', 50000, 'JS1', '2019-01-01', 'pendaftar pertama'),
('SMP00003', 'AG0006', 'Tri Fitriana Putri', 50000, 'JS1', '2019-01-01', 'pendaftar pertama'),
('SMP00004', 'AG0005', 'Fina Maghfiroh', 50000, 'JS1', '2019-01-01', 'pendaftar pertama'),
('SMP00005', 'AG0004', 'Ismi Nururrizqi', 50000, 'JS1', '2019-01-01', 'pendaftar pertama'),
('SMP00006', 'AG0003', 'uzumaki', 50000, 'JS1', '2019-01-01', 'pendaftar pertama'),
('SMP00007', 'AG0002', 'Saevia IZ', 50000, 'JS1', '2019-07-01', 'pendaftar pertama'),
('SMP00008', 'AG0009', 'KOPERASI NR', 1200000, 'JS4', '2019-01-02', 'penyaluran dana dari bang xyz'),
('SMP00009', 'AG0009', 'KOPERASI NR', 700000, 'JS4', '2019-01-03', 'penyaluran dana dari PT sejahtera'),
('SMP00010', 'AG0006', 'Tri Fitriana Putri', 200000, 'JS3', '2019-02-12', 'tabungan');

-- --------------------------------------------------------

--
-- Table structure for table `tenor`
--

CREATE TABLE `tenor` (
  `id` varchar(4) NOT NULL,
  `tenor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tenor`
--

INSERT INTO `tenor` (`id`, `tenor`) VALUES
('TR3', 3),
('TR6', 6);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `logged_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `logged_at`, `created_at`, `updated_at`) VALUES
(1, 'aXNtaQ==', 'aXNtaQ==', '2019-07-11 06:31:47', '2019-06-25 03:09:44', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `no_hp` (`no_hp`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `nik` (`nik`),
  ADD KEY `pekerjaan` (`pekerjaan`);

--
-- Indexes for table `angsuran_pinjaman`
--
ALTER TABLE `angsuran_pinjaman`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_pinjam` (`id_pinjam`);

--
-- Indexes for table `jenis_simpanan`
--
ALTER TABLE `jenis_simpanan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pekerjaan`
--
ALTER TABLE `pekerjaan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pengeluaran`
--
ALTER TABLE `pengeluaran`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pinjaman`
--
ALTER TABLE `pinjaman`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_angg`),
  ADD KEY `tenor` (`tenor`);

--
-- Indexes for table `simpanan`
--
ALTER TABLE `simpanan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_angg`),
  ADD KEY `jns_simpan` (`jns_simpan`);

--
-- Indexes for table `tenor`
--
ALTER TABLE `tenor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `angsuran_pinjaman`
--
ALTER TABLE `angsuran_pinjaman`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `pekerjaan`
--
ALTER TABLE `pekerjaan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `anggota`
--
ALTER TABLE `anggota`
  ADD CONSTRAINT `anggota_ibfk_1` FOREIGN KEY (`pekerjaan`) REFERENCES `pekerjaan` (`id`);

--
-- Constraints for table `angsuran_pinjaman`
--
ALTER TABLE `angsuran_pinjaman`
  ADD CONSTRAINT `angsuran_pinjaman_ibfk_1` FOREIGN KEY (`id_pinjam`) REFERENCES `pinjaman` (`id`);

--
-- Constraints for table `pinjaman`
--
ALTER TABLE `pinjaman`
  ADD CONSTRAINT `pinjaman_ibfk_1` FOREIGN KEY (`id_angg`) REFERENCES `anggota` (`id`),
  ADD CONSTRAINT `pinjaman_ibfk_2` FOREIGN KEY (`tenor`) REFERENCES `tenor` (`id`);

--
-- Constraints for table `simpanan`
--
ALTER TABLE `simpanan`
  ADD CONSTRAINT `simpanan_ibfk_1` FOREIGN KEY (`id_angg`) REFERENCES `anggota` (`id`),
  ADD CONSTRAINT `simpanan_ibfk_2` FOREIGN KEY (`jns_simpan`) REFERENCES `jenis_simpanan` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
