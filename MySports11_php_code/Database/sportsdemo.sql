-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 03, 2021 at 02:56 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sportsdemo`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_control`
--

CREATE TABLE `admin_control` (
  `ControlID` tinyint(2) UNSIGNED NOT NULL,
  `ControlName` varchar(50) NOT NULL,
  `ModuleID` tinyint(2) UNSIGNED DEFAULT NULL,
  `ParentControlID` tinyint(2) UNSIGNED DEFAULT NULL,
  `Sort` tinyint(1) UNSIGNED NOT NULL,
  `ModuleIcon` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin_control`
--

INSERT INTO `admin_control` (`ControlID`, `ControlName`, `ModuleID`, `ParentControlID`, `Sort`, `ModuleIcon`) VALUES
(1, 'Dashboard', 1, NULL, 1, 'fa fa-home'),
(2, 'Admin', NULL, NULL, 2, 'fa fa-user-circle-o'),
(3, 'Staff Members', 2, 2, 1, NULL),
(5, 'User', NULL, NULL, 3, 'fa fa-group'),
(6, 'Users', 3, 5, 1, ''),
(7, 'Social', NULL, NULL, 4, NULL),
(8, 'Reported Contents', 4, 7, 1, NULL),
(9, 'Configuration', NULL, NULL, 5, 'fa fa-cogs'),
(10, 'Categories', 5, 9, 1, NULL),
(11, 'Products', 6, 12, 2, NULL),
(12, 'Store', NULL, NULL, 4, 'fa fa-university'),
(13, 'Stores', 7, 12, 1, ''),
(14, 'Orders', 8, 12, 3, NULL),
(15, 'Coupon', 9, 12, 4, NULL),
(16, 'Bookings', 10, 12, 3, NULL),
(17, 'Broadcast', 11, 5, 2, NULL),
(18, 'Manage Pages', 12, 9, 2, NULL),
(19, 'Sales Report', 13, 12, 4, NULL),
(36, 'Cricket', NULL, NULL, 4, 'fa fa-soccer-ball-o'),
(37, 'Series', 15, 36, 1, NULL),
(38, 'Matches', 16, 36, 2, NULL),
(39, 'Contests', 17, 36, 4, NULL),
(40, 'Teams', 18, 36, 3, NULL),
(41, 'Point System', 19, 36, 9, NULL),
(44, 'Withdrawals', 23, 5, 3, NULL),
(45, 'Winnings', 26, 36, 8, NULL),
(46, 'Verifications', 27, 5, 4, NULL),
(47, 'Settings', 28, 9, 1, NULL),
(48, 'Manage Testimonial', 29, 5, 5, NULL),
(49, 'Banner', 30, 9, 1, NULL),
(51, 'Private Contests', 33, 36, 5, NULL),
(52, 'Pre Contest', 35, 36, 7, NULL),
(53, 'Setup', NULL, NULL, 1, 'fa fa-group'),
(54, 'Roles/Permissions', 37, 53, 1, NULL),
(65, 'Unverified Users', 51, 5, 1, ''),
(74, 'Upcoming Matches', 61, 36, 1, ''),
(78, 'Lucky Wheel Reports', 68, 66, 1, ''),
(87, 'Upcoming Matches', 78, 55, 1, ''),
(89, 'Account Reports', 81, 88, 1, ''),
(91, 'Contest Analysis', 84, 90, 3, ''),
(92, 'Account Analysis', 85, 90, 1, ''),
(93, 'Match Analysis', 86, 90, 2, ''),
(99, 'Auction Private Contests', 89, 95, 6, NULL),
(104, 'Private Contest Analysis', 93, 66, 3, ''),
(105, 'Private Contest Analysis', 94, 80, 2, NULL),
(106, 'Contest Report', 95, 88, 1, ''),
(110, 'Subscriptions', 98, 9, 1, NULL),
(111, 'Reports', NULL, NULL, 4, 'fa fa-file'),
(112, 'Matches Report', 99, 111, 1, NULL),
(113, 'Daily Invoice Report', 100, 111, 2, NULL),
(114, 'Wallet Details', 102, 111, 3, NULL),
(115, 'Cron List', 103, 9, 5, NULL),
(116, 'Offers', 104, 9, 6, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `admin_modules`
--

CREATE TABLE `admin_modules` (
  `ModuleID` tinyint(2) UNSIGNED NOT NULL,
  `ModuleTitle` varchar(50) NOT NULL,
  `ModuleName` varchar(50) NOT NULL,
  `ModelType` int(1) DEFAULT NULL COMMENT '1 = cricket , 2 = football , 3 = settings',
  `Sort` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin_modules`
--

INSERT INTO `admin_modules` (`ModuleID`, `ModuleTitle`, `ModuleName`, `ModelType`, `Sort`) VALUES
(1, 'Dashboard', 'dashboard', 1, NULL),
(2, 'Manage Staff', 'staff', 1, NULL),
(3, 'Manage Users', 'user', 1, NULL),
(4, 'Reported Content', 'flagged', 1, NULL),
(5, 'Manage Categories', 'category', 1, NULL),
(6, 'Manage Products', 'product', 1, NULL),
(7, 'Manage Stores', 'store', 1, NULL),
(8, 'Manage Orders', 'order', 1, NULL),
(9, 'Manage Coupons', 'coupon', 1, NULL),
(10, 'Manage Bookings', 'booking', 1, NULL),
(11, 'Broadcast Message', 'broadcast', 1, NULL),
(12, 'Manage Page', 'page', 1, NULL),
(13, 'Sales Report', 'storesalesreport', 1, NULL),
(15, 'Series', 'series', 1, NULL),
(16, 'Matches', 'matches', 1, NULL),
(17, 'Contests', 'contests', 1, NULL),
(18, 'Teams', 'teams', 1, NULL),
(19, 'Point System', 'pointsystem', 1, NULL),
(20, 'Players', 'players', 1, NULL),
(22, 'Transactions', 'transactions', 1, NULL),
(23, 'Withdrawals', 'withdrawals', 1, NULL),
(24, 'Joined Contests', 'joinedcontests', 1, NULL),
(25, 'Private Contests', 'privatecontests', 1, NULL),
(26, 'Winnings', 'winnings', 1, NULL),
(27, 'Verifications', 'verifications', 1, NULL),
(28, 'Settings', 'bonus', 1, NULL),
(29, 'Manage Testimonial', 'post', 1, NULL),
(30, 'Banner', 'banner', 1, NULL),
(31, 'UserDetails', 'userdetails', 1, NULL),
(32, 'AuctionDrafts', 'auctionDrafts', 2, NULL),
(33, 'Private Contests', 'Privatecontests', 1, NULL),
(34, 'Deposit History', 'depositHistory', 1, NULL),
(35, 'Pre Contest', 'predraft', 1, NULL),
(36, 'Notification List', 'NotificationList', 1, NULL),
(37, 'Roles/Permissions', 'setup/group', 1, NULL),
(38, 'Series Round List', 'roundList', 2, NULL),
(39, 'Series Round List', 'roundList', 1, NULL),
(40, 'Football Series', 'football/series', 2, NULL),
(41, 'Football Matches', 'football/matches', 2, NULL),
(42, 'Football Contests', 'football/contests', 2, NULL),
(43, 'Football Teams', 'football/teams', 2, NULL),
(44, 'Football Point System', 'football/pointsystem', 2, NULL),
(45, 'Football Winnings', 'football/winnings', 2, NULL),
(46, 'Football AuctionDrafts', 'football/auctionDrafts', 2, NULL),
(47, 'Football Private Contests', 'football/Privatecontests', 2, NULL),
(48, 'Football Pre Contest', 'football/predraft', 2, NULL),
(49, 'Football/Series Round List', 'football/roundList', 2, NULL),
(50, 'Football Players', 'football/players', 2, NULL),
(51, 'Unverified Users', 'user?pending=pending', 1, NULL),
(52, 'Series Round List', 'roundList', 2, NULL),
(53, 'Cricket Match Analysis', 'reports', 2, 1),
(54, 'Cricket Account Analysis', 'reports/account', 2, 1),
(55, 'Cricket User Analysis', 'reports/userAnalysis', 2, 1),
(56, 'Cricket Contest Analysis', 'reports/contestAnalysis', 2, 1),
(58, 'User Registration Analysis', 'reports/userRegistrationReports', 2, 1),
(59, 'User Joined Fee', 'reports/userJoinedReports', 2, 1),
(60, 'User Planning Lifetime', 'reports/userPlanningLifetime', 2, 1),
(61, 'Upcoming Matches', 'matches/upcomingMatches', 1, 1),
(62, 'Manage Players', 'players/managePlayers', 1, NULL),
(63, 'AuctionDrafts', 'auctionDrafts/auctionDraftsAdd', 2, NULL),
(64, 'AuctionDrafts', 'auctionDrafts/auctionDraftsEdit', 2, NULL),
(65, 'Lucky Wheel', 'LuckyWheel', 1, NULL),
(66, 'Lucky Wheel', 'LuckyWheel/transtion_list', 1, NULL),
(67, 'SnakeDrafts', 'auctionDrafts/snake', 2, NULL),
(68, 'Lucky Wheel Report', 'LuckyWheel/report', 2, NULL),
(69, 'Pre Auction Contest', 'predraft/preAuctionList', 2, NULL),
(70, 'Pre Auction Add Contest', 'predraft/preAuctionAdd', 2, NULL),
(71, 'Football Account Analysis', 'football/reports/account', 2, 1),
(72, 'Football Match Analysis', 'football/reports', 2, 1),
(73, 'Football User Analysis', 'football/reports/userAnalysis', 2, 1),
(74, 'Football Contest Analysis', 'football/reports/contestAnalysis', 2, 1),
(75, 'Football User Registration Analysis', 'football/reports/userRegistrationReports', 2, 1),
(76, 'Football User Joined Fee', 'football/reports/userJoinedReports', 2, 1),
(77, 'Football User Planning Lifetime', 'football/reports/userPlanningLifetime', 2, 1),
(78, 'Upcoming Matches', 'football/matches/upcomingMatches', 2, 1),
(79, 'Match All Contest reports', 'reports/matchContestAnalysis', 2, NULL),
(80, 'Football Match All Contest Analysis', 'football/reports/matchContestAnalysis', 2, NULL),
(81, 'Account Reports', 'auctionReports/account', 2, NULL),
(82, 'Series All Contests Reports', 'auctionReports/matchContestAnalysis', 2, NULL),
(83, 'Gully Match All Contest Analysis', 'gullyReports/matchContestAnalysis', 2, NULL),
(84, 'Gully Cricket Contest Analysis', 'gullyReports/contestAnalysis', 2, 3),
(85, 'Gully Cricket Account Analysis', 'gullyReports/account', 2, 1),
(86, 'Gully Cricket Match Analysis', 'gullyReports', 2, 2),
(87, 'Virtual Matches', 'matches/virtualMatches', 2, 1),
(88, 'Pre Snake Contest', 'predraft/preSnakeList', 2, 1),
(89, 'Auction Private Contests ', 'Privatecontests/AuctionPrivateContests', 2, 2),
(90, 'Snake Private Contests ', 'Privatecontests/SnakePrivateContests', 2, 3),
(91, 'Broadcast Scheduling', 'broadcast/scheduling', 1, NULL),
(92, 'Virtual Contests', 'virtualContests', 2, NULL),
(93, 'Private Cricket Contest Analysis', 'reports/privateContestAnalysis', 2, 1),
(94, 'Private Football Contest Analysis', 'football/reports/privateContestAnalysis', 2, 4),
(95, 'Cricket Auction Contests Reports', 'auctionReports/contestAnalysis', 2, NULL),
(96, 'Cricket virtual Contest Analysis', 'reports/virtualContestAnalysis', 2, 1),
(97, 'Match Broadcast Scheduling', 'broadcast/matchBroadcast', 2, NULL),
(98, 'subscription', 'subscription', 1, NULL),
(99, 'Matches Report', 'matchreport', 1, NULL),
(100, 'Daily Invoice Report', 'dailyInvoiceReport', 1, NULL),
(102, 'Wallet Details', 'dashboard/checkWalletBalance', 1, NULL),
(103, 'Cron List', 'user/allCrons', 1, NULL),
(104, 'Offers', 'offers', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `admin_user_type_permission`
--

CREATE TABLE `admin_user_type_permission` (
  `UserTypeID` int(11) NOT NULL,
  `ModuleID` tinyint(2) UNSIGNED NOT NULL,
  `IsDefault` enum('Yes') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin_user_type_permission`
--

INSERT INTO `admin_user_type_permission` (`UserTypeID`, `ModuleID`, `IsDefault`) VALUES
(1, 1, NULL),
(1, 2, NULL),
(1, 3, NULL),
(1, 5, NULL),
(1, 9, NULL),
(1, 11, NULL),
(1, 15, NULL),
(1, 16, NULL),
(1, 17, NULL),
(1, 18, NULL),
(1, 19, NULL),
(1, 20, NULL),
(1, 22, NULL),
(1, 23, NULL),
(1, 24, NULL),
(1, 25, NULL),
(1, 26, NULL),
(1, 27, NULL),
(1, 28, NULL),
(1, 29, NULL),
(1, 30, NULL),
(1, 31, NULL),
(1, 32, NULL),
(1, 33, NULL),
(1, 34, NULL),
(1, 35, NULL),
(1, 36, NULL),
(1, 37, NULL),
(1, 40, NULL),
(1, 41, NULL),
(1, 42, NULL),
(1, 43, NULL),
(1, 44, NULL),
(1, 45, NULL),
(1, 46, NULL),
(1, 47, NULL),
(1, 48, NULL),
(1, 49, NULL),
(1, 50, NULL),
(1, 51, NULL),
(1, 52, NULL),
(1, 53, NULL),
(1, 54, NULL),
(1, 55, NULL),
(1, 56, NULL),
(1, 58, NULL),
(1, 59, NULL),
(1, 60, NULL),
(1, 61, NULL),
(1, 62, NULL),
(1, 63, NULL),
(1, 64, NULL),
(1, 65, NULL),
(1, 66, NULL),
(1, 67, NULL),
(1, 68, NULL),
(1, 69, NULL),
(1, 70, NULL),
(1, 71, NULL),
(1, 72, NULL),
(1, 73, NULL),
(1, 74, NULL),
(1, 75, NULL),
(1, 76, NULL),
(1, 77, NULL),
(1, 78, NULL),
(1, 79, NULL),
(1, 80, NULL),
(1, 81, NULL),
(1, 82, NULL),
(1, 83, NULL),
(1, 84, NULL),
(1, 85, NULL),
(1, 86, NULL),
(1, 87, NULL),
(1, 88, NULL),
(1, 89, NULL),
(1, 90, NULL),
(1, 91, NULL),
(1, 92, NULL),
(1, 93, NULL),
(1, 94, NULL),
(1, 95, NULL),
(1, 96, NULL),
(1, 97, NULL),
(1, 98, NULL),
(1, 99, NULL),
(1, 100, NULL),
(1, 102, NULL),
(1, 103, NULL),
(1, 104, NULL),
(2, 12, NULL),
(4, 1, NULL),
(4, 15, NULL),
(4, 16, NULL),
(4, 17, NULL),
(4, 20, NULL),
(4, 23, NULL),
(4, 27, NULL),
(4, 35, NULL),
(4, 61, NULL),
(4, 62, NULL),
(4, 91, NULL),
(4, 103, NULL),
(5, 1, NULL),
(5, 9, NULL),
(5, 99, NULL),
(5, 100, NULL),
(5, 102, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `banner`
--

CREATE TABLE `banner` (
  `BannerID` int(11) NOT NULL,
  `BannerTitle` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ecom_coupon`
--

CREATE TABLE `ecom_coupon` (
  `CouponID` int(11) NOT NULL,
  `CouponTitle` varchar(255) DEFAULT NULL,
  `CouponDescription` mediumtext DEFAULT NULL,
  `CouponCode` varchar(12) NOT NULL,
  `CouponType` enum('Flat','Percentage') NOT NULL,
  `OfferType` enum('CashBonus','RealMoney') NOT NULL DEFAULT 'CashBonus',
  `DepositType` enum('FirstDeposit','AllDeposits') NOT NULL DEFAULT 'AllDeposits',
  `CouponValue` int(11) NOT NULL,
  `CouponValueLimit` int(11) DEFAULT NULL,
  `MiniumAmount` mediumint(8) DEFAULT NULL,
  `MaximumAmount` mediumint(8) DEFAULT NULL,
  `NumberOfUses` smallint(6) DEFAULT NULL,
  `CouponValidTillDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_contest`
--

CREATE TABLE `football_sports_contest` (
  `ContestID` int(11) NOT NULL,
  `ContestGUID` varchar(36) NOT NULL,
  `UserID` int(11) NOT NULL,
  `LeagueType` enum('Dfs','Draft','Auction','') NOT NULL DEFAULT 'Dfs',
  `GameType` enum('Advance','Safe') NOT NULL DEFAULT 'Advance',
  `GameTimeLive` smallint(6) DEFAULT 0,
  `LeagueJoinDateTime` datetime DEFAULT NULL,
  `AdminPercent` smallint(6) DEFAULT NULL,
  `ContestFormat` enum('Head to Head','League') NOT NULL,
  `ContestType` enum('Normal','Reverse','InPlay','Hot','Champion','Practice','More','Mega','Winner Takes All','Only For Beginners','Head to Head','Smart Pool','Infinity Pool') NOT NULL,
  `IsAutoCreate` enum('No','Yes') NOT NULL DEFAULT 'No',
  `PreContestID` int(11) DEFAULT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `ContestName` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
  `Privacy` enum('Yes','No') NOT NULL,
  `IsPaid` enum('Yes','No') NOT NULL,
  `IsConfirm` enum('Yes','No') NOT NULL DEFAULT 'No',
  `ShowJoinedContest` enum('Yes','No') NOT NULL,
  `WinningAmount` int(11) NOT NULL DEFAULT 0,
  `ContestSize` int(11) NOT NULL,
  `TotalJoinedTeams` int(11) NOT NULL DEFAULT 0,
  `CashBonusContribution` float(6,2) NOT NULL,
  `UserJoinLimit` int(11) NOT NULL DEFAULT 1,
  `EntryType` enum('Single','Multiple') NOT NULL,
  `UnfilledWinningPercent` enum('GuranteedPool','Fixed') DEFAULT 'Fixed',
  `WinUpTo` float(4,2) NOT NULL DEFAULT 0.00,
  `WinningRatio` int(11) NOT NULL DEFAULT 0,
  `SmartPool` enum('Yes','No') NOT NULL DEFAULT 'No',
  `EntryFee` int(11) NOT NULL,
  `NoOfWinners` int(11) NOT NULL,
  `CustomizeWinning` text DEFAULT NULL,
  `UserInvitationCode` varchar(50) DEFAULT NULL,
  `IsWinningDistributed` enum('No','Yes') NOT NULL DEFAULT 'No',
  `MinimumUserJoined` int(11) DEFAULT NULL,
  `AuctionStatusID` int(11) NOT NULL DEFAULT 1,
  `AuctionUpdateTime` datetime DEFAULT NULL,
  `AuctionTimeBreakAvailable` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionIsBreakTimeStatus` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionBreakDateTime` datetime DEFAULT NULL,
  `DraftTotalRounds` smallint(6) DEFAULT NULL,
  `DraftLiveRound` smallint(6) DEFAULT NULL,
  `DraftUserTeamSubmitted` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsRefund` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistribute` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistributeAmount` enum('Yes','No') NOT NULL DEFAULT 'No',
  `ContestTransferred` enum('No','Yes') NOT NULL DEFAULT 'No',
  `isMailSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsVirtualUserJoined` enum('Yes','No','Completed') NOT NULL DEFAULT 'No',
  `IsDummyJoined` tinyint(4) NOT NULL DEFAULT 0,
  `VirtualUserJoinedPercentage` int(6) DEFAULT NULL,
  `DraftTeamPlayerLimit` smallint(6) DEFAULT NULL,
  `DraftPlayerSelectionCriteria` varchar(255) DEFAULT NULL,
  `IsVirtualTeamCreated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `PointSystem` text DEFAULT NULL,
  `IsCalculateCaptainVC` enum('Yes','No') NOT NULL DEFAULT 'Yes',
  `IsPrivacyNameDisplay` enum('Yes','No') DEFAULT 'No',
  `CancelledBy` enum('Admin','Cron') DEFAULT 'Cron'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_contest_join`
--

CREATE TABLE `football_sports_contest_join` (
  `ContestID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `SeriesID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `JoinInning` enum('First','Second','Third','Fourth') DEFAULT NULL,
  `UserTeamID` int(11) DEFAULT NULL,
  `JoinWalletAmount` float(8,2) DEFAULT 0.00,
  `JoinWinningAmount` float(8,2) NOT NULL DEFAULT 0.00,
  `JoinCashBonus` float(8,2) NOT NULL DEFAULT 0.00,
  `TotalPoints` float(8,2) NOT NULL,
  `UserRank` int(11) DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  `UserWinningAmount` float(8,2) NOT NULL,
  `SmartPool` enum('Yes','No') NOT NULL DEFAULT 'No',
  `SmartPoolWinning` varchar(255) DEFAULT NULL,
  `EntryDate` datetime NOT NULL,
  `AuctionTimeBank` smallint(6) NOT NULL DEFAULT 180,
  `AuctionBudget` bigint(20) NOT NULL DEFAULT 1000000000,
  `AuctionUserStatus` enum('Online','Offline') NOT NULL DEFAULT 'Offline',
  `IsHold` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionHoldDateTime` datetime DEFAULT NULL,
  `DraftUserPosition` smallint(6) DEFAULT NULL,
  `DraftUserLive` enum('Yes','No') NOT NULL DEFAULT 'No',
  `DraftUserLiveTime` datetime DEFAULT NULL,
  `IsRefund` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistribute` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistributeAmount` enum('Yes','No') NOT NULL DEFAULT 'No',
  `isMailSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `CopiedTeam` int(11) NOT NULL DEFAULT 0,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_matches`
--

CREATE TABLE `football_sports_matches` (
  `MatchID` int(11) NOT NULL,
  `MatchGUID` char(36) NOT NULL,
  `MatchIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `MatchTypeID` int(11) NOT NULL,
  `MatchNo` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `MatchLocation` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
  `TeamIDLocal` int(11) NOT NULL,
  `TeamIDVisitor` int(11) NOT NULL,
  `MatchStartDateTime` datetime DEFAULT NULL,
  `APIAutoTimeUpdate` enum('Yes','No') NOT NULL DEFAULT 'No',
  `MatchClosedInMinutes` smallint(6) DEFAULT NULL,
  `MatchCompleteDateTime` datetime DEFAULT NULL,
  `IsPreSquad` enum('Yes','No') NOT NULL DEFAULT 'No' COMMENT 'if yes matches going to user crate team ',
  `PlayerStatsUpdate` enum('Yes','No') NOT NULL DEFAULT 'No',
  `MatchScoreDetails` text DEFAULT NULL,
  `LastUpdatedOn` datetime DEFAULT NULL,
  `IsPlayingXINotificationSent` enum('No','Yes') NOT NULL DEFAULT 'No',
  `isReminderNotificationSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsPlayerPointsUpdated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsUserPointsUpdated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `isMailSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `MatchPospondNotUpdateDate` enum('Yes','No') NOT NULL DEFAULT 'No',
  `PointsLastUpdatedOn` datetime DEFAULT NULL,
  `MatchDisplay` enum('Enable','Disable') DEFAULT 'Enable',
  `AdminChangeRole` enum('No','Yes') DEFAULT 'No',
  `IsEdited` enum('No','Yes') DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_players`
--

CREATE TABLE `football_sports_players` (
  `PlayerID` int(11) NOT NULL,
  `PlayerGUID` varchar(36) NOT NULL,
  `PlayerIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `PlayerName` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `PlayerPic` varchar(50) DEFAULT NULL,
  `PlayerCountry` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `PlayerBattingStyle` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `PlayerBowlingStyle` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `PlayerBattingStats` text DEFAULT NULL,
  `PlayerBowlingStats` text DEFAULT NULL,
  `PlayerFieldingStats` text DEFAULT NULL,
  `PlayerStats` longtext DEFAULT NULL,
  `PlayerSalary` float(4,2) DEFAULT NULL,
  `IsAdminSalaryUpdated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `LastUpdatedOn` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_pre_contest`
--

CREATE TABLE `football_sports_pre_contest` (
  `PreContestID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `ContestFormat` enum('Head to Head','League') COLLATE utf8_unicode_ci NOT NULL,
  `UnfilledWinningPercent` enum('GuranteedPool','Fixed') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Fixed',
  `WinningRatio` int(11) NOT NULL DEFAULT 0,
  `WinUpTo` float(4,2) NOT NULL DEFAULT 0.00,
  `SmartPool` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `ContestType` enum('Normal','Reverse','InPlay','Hot','Champion','Practice','More','Mega','Winner Takes All','Only For Beginners','Head to Head','Smart Pool','Infinity Pool') COLLATE utf8_unicode_ci NOT NULL,
  `ContestName` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `Privacy` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL,
  `IsPaid` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL,
  `IsConfirm` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Yes',
  `IsAutoCreate` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `ShowJoinedContest` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL,
  `WinningAmount` int(11) NOT NULL DEFAULT 0,
  `ContestSize` int(11) NOT NULL,
  `CashBonusContribution` float(6,2) NOT NULL,
  `AdminPercent` float(6,2) NOT NULL,
  `UserJoinLimit` int(11) NOT NULL DEFAULT 6,
  `EntryType` enum('Single','Multiple') COLLATE utf8_unicode_ci NOT NULL,
  `EntryFee` int(11) NOT NULL,
  `NoOfWinners` int(11) NOT NULL,
  `CustomizeWinning` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `IsWinnerSocialFeed` enum('No','Yes') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No' COMMENT '(If Yes, then after contest winning distribution we will share contest winner information on facebook page )',
  `IsWinningDistributed` enum('No','Yes') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `IsVirtualUserJoined` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `VirtualUserJoinedPercentage` int(6) NOT NULL DEFAULT 0,
  `StatusID` int(1) DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_series`
--

CREATE TABLE `football_sports_series` (
  `SeriesID` int(11) NOT NULL,
  `SeriesGUID` char(36) NOT NULL,
  `SeriesIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `SeriesName` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `SeriesShortName` varchar(100) DEFAULT NULL,
  `SeriesType` varchar(50) DEFAULT NULL,
  `SeriesStartDate` date DEFAULT NULL,
  `SeriesEndDate` date DEFAULT NULL,
  `AuctionDraftIsPlayed` enum('Yes','No') NOT NULL DEFAULT 'Yes',
  `DraftUserLimit` int(11) NOT NULL DEFAULT 0 COMMENT 'Snake & Auction Draft',
  `DraftTeamPlayerLimit` smallint(6) DEFAULT NULL,
  `DraftPlayerSelectionCriteria` varchar(255) DEFAULT NULL,
  `AuctionDraftStatusID` int(11) NOT NULL DEFAULT 1,
  `CompetitionStateKey` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_series_rounds`
--

CREATE TABLE `football_sports_series_rounds` (
  `RoundID` int(11) NOT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `RoundName` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `SeriesType` varchar(50) DEFAULT NULL,
  `RoundFormat` varchar(50) DEFAULT NULL,
  `RoundStartDate` date DEFAULT NULL,
  `RoundEndDate` date DEFAULT NULL,
  `AuctionDraftIsPlayed` enum('Yes','No') NOT NULL DEFAULT 'Yes',
  `DraftUserLimit` int(11) NOT NULL DEFAULT 0 COMMENT 'Snake & Auction Draft',
  `DraftTeamPlayerLimit` smallint(6) DEFAULT NULL,
  `DraftPlayerSelectionCriteria` varchar(255) DEFAULT NULL,
  `AuctionDraftStatusID` int(11) NOT NULL DEFAULT 1,
  `StatusID` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_setting_points`
--

CREATE TABLE `football_sports_setting_points` (
  `PointsTypeGUID` varchar(36) NOT NULL,
  `StaticDescription` varchar(255) DEFAULT NULL,
  `PointsTypeDescprition` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `PointsTypeShortDescription` char(22) NOT NULL,
  `PointsType` enum('Goal Point','Miscellaneous') NOT NULL,
  `PointsInningType` enum('Goal') NOT NULL,
  `PointsScoringField` varchar(50) DEFAULT NULL,
  `PointsValue` float NOT NULL DEFAULT 0,
  `StatusID` int(11) NOT NULL DEFAULT 6,
  `Sort` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_set_match_types`
--

CREATE TABLE `football_sports_set_match_types` (
  `MatchTypeID` int(11) NOT NULL,
  `MatchTypeName` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '(Entity API)',
  `MatchTypeNameCricketAPI` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_teams`
--

CREATE TABLE `football_sports_teams` (
  `TeamID` int(11) NOT NULL,
  `TeamGUID` char(36) NOT NULL,
  `TeamIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `TeamName` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
  `TeamNameShort` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL,
  `TeamFlag` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_team_players`
--

CREATE TABLE `football_sports_team_players` (
  `id` int(11) NOT NULL,
  `PlayerID` int(11) NOT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `MatchID` int(11) NOT NULL,
  `TeamID` int(11) NOT NULL,
  `PlayerRole` enum('Goalkeeper','Defender','Midfielder','Forward','Other','Striker') NOT NULL,
  `PlayerSalary` float(8,2) NOT NULL DEFAULT 0.00,
  `IsPlaying` enum('Yes','No') NOT NULL DEFAULT 'No',
  `TotalPoints` float(8,2) DEFAULT 0.00,
  `PointsData` text DEFAULT NULL,
  `IsRemoved` enum('No','Yes') NOT NULL DEFAULT 'No',
  `IsAdminUpdate` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsActive` enum('Yes','No') NOT NULL DEFAULT 'Yes',
  `IsDublicate` enum('No','Yes') DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_users_teams`
--

CREATE TABLE `football_sports_users_teams` (
  `UserTeamID` int(11) NOT NULL,
  `UserTeamGUID` varchar(36) NOT NULL,
  `UserID` int(11) NOT NULL,
  `UserTeamName` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `UserTeamType` enum('Normal','Auction','Draft') NOT NULL DEFAULT 'Normal',
  `MatchID` int(11) DEFAULT NULL,
  `SeriesID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `ContestID` int(11) DEFAULT NULL,
  `IsPreTeam` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsAssistant` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsVirtual` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionTopPlayerSubmitted` enum('Yes','No') NOT NULL DEFAULT 'No',
  `MatchInning` enum('First','Second','Third','Fourth') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `football_sports_users_team_players`
--

CREATE TABLE `football_sports_users_team_players` (
  `UserTeamID` int(11) NOT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `PlayerID` int(11) NOT NULL,
  `PlayerPosition` enum('Player','Captain','ViceCaptain') NOT NULL,
  `Points` float(8,2) NOT NULL,
  `BidCredit` decimal(10,0) DEFAULT NULL,
  `SeriesID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `DateTime` datetime DEFAULT NULL,
  `AuctionPlayingPlayer` enum('No','Yes') NOT NULL DEFAULT 'No',
  `AuctionDraftAssistantPriority` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `logs_paytm`
--

CREATE TABLE `logs_paytm` (
  `ID` int(11) NOT NULL,
  `WalletID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `PayType` varchar(255) NOT NULL DEFAULT 'APP',
  `Response` mediumtext DEFAULT NULL,
  `AppResponse` mediumtext DEFAULT NULL,
  `EntryDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `log_api`
--

CREATE TABLE `log_api` (
  `LogID` int(11) NOT NULL,
  `URL` varchar(255) NOT NULL,
  `RawData` mediumtext NOT NULL,
  `DataJ` mediumtext NOT NULL,
  `Response` mediumtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `log_cron`
--

CREATE TABLE `log_cron` (
  `CronID` int(11) NOT NULL,
  `CronType` enum('getSeriesLive','getMatchesLive','getPlayersLive','getPlayerStatsLive','getMatchScoreLive','autoCancelContest','getPlayerPoints','getJoinedContestPlayerPoints','setContestWinners') NOT NULL,
  `EntryDate` datetime NOT NULL,
  `CronStatus` enum('Completed','Exit') DEFAULT NULL,
  `CompletionDate` datetime DEFAULT NULL,
  `CronResponse` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `log_pushdata`
--

CREATE TABLE `log_pushdata` (
  `ID` int(10) UNSIGNED NOT NULL,
  `Body` text DEFAULT NULL,
  `DeviceTypeID` int(11) DEFAULT NULL,
  `Return` varchar(255) DEFAULT NULL,
  `EntryDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `set_categories`
--

CREATE TABLE `set_categories` (
  `CategoryID` int(11) NOT NULL,
  `CategoryGUID` char(36) NOT NULL,
  `CategoryTypeID` int(11) NOT NULL,
  `ParentCategoryID` int(11) DEFAULT NULL,
  `CategoryName` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `set_categories_type`
--

CREATE TABLE `set_categories_type` (
  `CategoryTypeID` int(11) NOT NULL,
  `CategoryTypeName` varchar(100) DEFAULT NULL,
  `StatusID` int(11) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `set_categories_type`
--

INSERT INTO `set_categories_type` (`CategoryTypeID`, `CategoryTypeName`, `StatusID`) VALUES
(1, 'Category Type1', 2);

-- --------------------------------------------------------

--
-- Table structure for table `set_device_type`
--

CREATE TABLE `set_device_type` (
  `DeviceTypeID` int(11) NOT NULL,
  `DeviceTypeName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `set_device_type`
--

INSERT INTO `set_device_type` (`DeviceTypeID`, `DeviceTypeName`) VALUES
(1, 'Native'),
(2, 'iPhone'),
(3, 'AndroidPhone'),
(4, 'iPad'),
(5, 'AndroidTablet'),
(6, 'WindowsPhone'),
(7, 'WindowsTablet'),
(8, 'OtherMobileDevice');

-- --------------------------------------------------------

--
-- Table structure for table `set_location_country`
--

CREATE TABLE `set_location_country` (
  `CountryCode` char(2) NOT NULL,
  `CountryName` varchar(80) NOT NULL,
  `iso3` char(3) DEFAULT NULL,
  `phonecode` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `set_location_country`
--

INSERT INTO `set_location_country` (`CountryCode`, `CountryName`, `iso3`, `phonecode`) VALUES
('AF', 'Afghanistan', 'AFG', 93),
('AL', 'Albania', 'ALB', 355),
('DZ', 'Algeria', 'DZA', 213),
('AS', 'American Samoa', 'ASM', 1684),
('AD', 'Andorra', 'AND', 376),
('AO', 'Angola', 'AGO', 244),
('AI', 'Anguilla', 'AIA', 1264),
('AG', 'Antigua and Barbuda', 'ATG', 1268),
('AR', 'Argentina', 'ARG', 54),
('AM', 'Armenia', 'ARM', 374),
('AW', 'Aruba', 'ABW', 297),
('AU', 'Australia', 'AUS', 61),
('AT', 'Austria', 'AUT', 43),
('AZ', 'Azerbaijan', 'AZE', 994),
('BS', 'Bahamas', 'BHS', 1242),
('BH', 'Bahrain', 'BHR', 973),
('BD', 'Bangladesh', 'BGD', 880),
('BB', 'Barbados', 'BRB', 1246),
('BY', 'Belarus', 'BLR', 375),
('BE', 'Belgium', 'BEL', 32),
('BZ', 'Belize', 'BLZ', 501),
('BJ', 'Benin', 'BEN', 229),
('BM', 'Bermuda', 'BMU', 1441),
('BT', 'Bhutan', 'BTN', 975),
('BO', 'Bolivia', 'BOL', 591),
('BA', 'Bosnia and Herzegovina', 'BIH', 387),
('BW', 'Botswana', 'BWA', 267),
('BR', 'Brazil', 'BRA', 55),
('IO', 'British Indian Ocean Territory', NULL, 246),
('BN', 'Brunei Darussalam', 'BRN', 673),
('BG', 'Bulgaria', 'BGR', 359),
('BF', 'Burkina Faso', 'BFA', 226),
('BI', 'Burundi', 'BDI', 257),
('KH', 'Cambodia', 'KHM', 855),
('CM', 'Cameroon', 'CMR', 237),
('CA', 'Canada', 'CAN', 1),
('CV', 'Cape Verde', 'CPV', 238),
('KY', 'Cayman Islands', 'CYM', 1345),
('CF', 'Central African Republic', 'CAF', 236),
('TD', 'Chad', 'TCD', 235),
('CL', 'Chile', 'CHL', 56),
('CN', 'China', 'CHN', 86),
('CX', 'Christmas Island', NULL, 61),
('CC', 'Cocos (Keeling) Islands', NULL, 672),
('CO', 'Colombia', 'COL', 57),
('KM', 'Comoros', 'COM', 269),
('CG', 'Congo', 'COG', 242),
('CD', 'Congo, the Democratic Republic of the', 'COD', 242),
('CK', 'Cook Islands', 'COK', 682),
('CR', 'Costa Rica', 'CRI', 506),
('CI', 'Cote D\'Ivoire', 'CIV', 225),
('HR', 'Croatia', 'HRV', 385),
('CU', 'Cuba', 'CUB', 53),
('CY', 'Cyprus', 'CYP', 357),
('CZ', 'Czech Republic', 'CZE', 420),
('DK', 'Denmark', 'DNK', 45),
('DJ', 'Djibouti', 'DJI', 253),
('DM', 'Dominica', 'DMA', 1767),
('DO', 'Dominican Republic', 'DOM', 1809),
('EC', 'Ecuador', 'ECU', 593),
('EG', 'Egypt', 'EGY', 20),
('SV', 'El Salvador', 'SLV', 503),
('GQ', 'Equatorial Guinea', 'GNQ', 240),
('ER', 'Eritrea', 'ERI', 291),
('EE', 'Estonia', 'EST', 372),
('ET', 'Ethiopia', 'ETH', 251),
('FK', 'Falkland Islands (Malvinas)', 'FLK', 500),
('FO', 'Faroe Islands', 'FRO', 298),
('FJ', 'Fiji', 'FJI', 679),
('FI', 'Finland', 'FIN', 358),
('FR', 'France', 'FRA', 33),
('GF', 'French Guiana', 'GUF', 594),
('PF', 'French Polynesia', 'PYF', 689),
('GA', 'Gabon', 'GAB', 241),
('GM', 'Gambia', 'GMB', 220),
('GE', 'Georgia', 'GEO', 995),
('DE', 'Germany', 'DEU', 49),
('GH', 'Ghana', 'GHA', 233),
('GI', 'Gibraltar', 'GIB', 350),
('GR', 'Greece', 'GRC', 30),
('GL', 'Greenland', 'GRL', 299),
('GD', 'Grenada', 'GRD', 1473),
('GP', 'Guadeloupe', 'GLP', 590),
('GU', 'Guam', 'GUM', 1671),
('GT', 'Guatemala', 'GTM', 502),
('GN', 'Guinea', 'GIN', 224),
('GW', 'Guinea-Bissau', 'GNB', 245),
('GY', 'Guyana', 'GUY', 592),
('HT', 'Haiti', 'HTI', 509),
('VA', 'Holy See (Vatican City State)', 'VAT', 39),
('HN', 'Honduras', 'HND', 504),
('HK', 'Hong Kong', 'HKG', 852),
('HU', 'Hungary', 'HUN', 36),
('IS', 'Iceland', 'ISL', 354),
('IN', 'India', 'IND', 91),
('ID', 'Indonesia', 'IDN', 62),
('IR', 'Iran, Islamic Republic of', 'IRN', 98),
('IQ', 'Iraq', 'IRQ', 964),
('IE', 'Ireland', 'IRL', 353),
('IL', 'Israel', 'ISR', 972),
('IT', 'Italy', 'ITA', 39),
('JM', 'Jamaica', 'JAM', 1876),
('JP', 'Japan', 'JPN', 81),
('JO', 'Jordan', 'JOR', 962),
('KZ', 'Kazakhstan', 'KAZ', 7),
('KE', 'Kenya', 'KEN', 254),
('KI', 'Kiribati', 'KIR', 686),
('KP', 'Korea, Democratic People\'s Republic of', 'PRK', 850),
('KR', 'Korea, Republic of', 'KOR', 82),
('KW', 'Kuwait', 'KWT', 965),
('KG', 'Kyrgyzstan', 'KGZ', 996),
('LA', 'Lao People\'s Democratic Republic', 'LAO', 856),
('LV', 'Latvia', 'LVA', 371),
('LB', 'Lebanon', 'LBN', 961),
('LS', 'Lesotho', 'LSO', 266),
('LR', 'Liberia', 'LBR', 231),
('LY', 'Libyan Arab Jamahiriya', 'LBY', 218),
('LI', 'Liechtenstein', 'LIE', 423),
('LT', 'Lithuania', 'LTU', 370),
('LU', 'Luxembourg', 'LUX', 352),
('MO', 'Macao', 'MAC', 853),
('MK', 'Macedonia, the Former Yugoslav Republic of', 'MKD', 389),
('MG', 'Madagascar', 'MDG', 261),
('MW', 'Malawi', 'MWI', 265),
('MY', 'Malaysia', 'MYS', 60),
('MV', 'Maldives', 'MDV', 960),
('ML', 'Mali', 'MLI', 223),
('MT', 'Malta', 'MLT', 356),
('MH', 'Marshall Islands', 'MHL', 692),
('MQ', 'Martinique', 'MTQ', 596),
('MR', 'Mauritania', 'MRT', 222),
('MU', 'Mauritius', 'MUS', 230),
('YT', 'Mayotte', NULL, 269),
('MX', 'Mexico', 'MEX', 52),
('FM', 'Micronesia, Federated States of', 'FSM', 691),
('MD', 'Moldova, Republic of', 'MDA', 373),
('MC', 'Monaco', 'MCO', 377),
('MN', 'Mongolia', 'MNG', 976),
('MS', 'Montserrat', 'MSR', 1664),
('MA', 'Morocco', 'MAR', 212),
('MZ', 'Mozambique', 'MOZ', 258),
('MM', 'Myanmar', 'MMR', 95),
('NA', 'Namibia', 'NAM', 264),
('NR', 'Nauru', 'NRU', 674),
('NP', 'Nepal', 'NPL', 977),
('NL', 'Netherlands', 'NLD', 31),
('AN', 'Netherlands Antilles', 'ANT', 599),
('NC', 'New Caledonia', 'NCL', 687),
('NZ', 'New Zealand', 'NZL', 64),
('NI', 'Nicaragua', 'NIC', 505),
('NE', 'Niger', 'NER', 227),
('NG', 'Nigeria', 'NGA', 234),
('NU', 'Niue', 'NIU', 683),
('NF', 'Norfolk Island', 'NFK', 672),
('MP', 'Northern Mariana Islands', 'MNP', 1670),
('NO', 'Norway', 'NOR', 47),
('OM', 'Oman', 'OMN', 968),
('PK', 'Pakistan', 'PAK', 92),
('PW', 'Palau', 'PLW', 680),
('PS', 'Palestinian Territory, Occupied', NULL, 970),
('PA', 'Panama', 'PAN', 507),
('PG', 'Papua New Guinea', 'PNG', 675),
('PY', 'Paraguay', 'PRY', 595),
('PE', 'Peru', 'PER', 51),
('PH', 'Philippines', 'PHL', 63),
('PL', 'Poland', 'POL', 48),
('PT', 'Portugal', 'PRT', 351),
('PR', 'Puerto Rico', 'PRI', 1787),
('QA', 'Qatar', 'QAT', 974),
('RE', 'Reunion', 'REU', 262),
('RO', 'Romania', 'ROM', 40),
('RU', 'Russian Federation', 'RUS', 70),
('RW', 'Rwanda', 'RWA', 250),
('SH', 'Saint Helena', 'SHN', 290),
('KN', 'Saint Kitts and Nevis', 'KNA', 1869),
('LC', 'Saint Lucia', 'LCA', 1758),
('PM', 'Saint Pierre and Miquelon', 'SPM', 508),
('VC', 'Saint Vincent and the Grenadines', 'VCT', 1784),
('WS', 'Samoa', 'WSM', 684),
('SM', 'San Marino', 'SMR', 378),
('ST', 'Sao Tome and Principe', 'STP', 239),
('SA', 'Saudi Arabia', 'SAU', 966),
('SN', 'Senegal', 'SEN', 221),
('CS', 'Serbia and Montenegro', NULL, 381),
('SC', 'Seychelles', 'SYC', 248),
('SL', 'Sierra Leone', 'SLE', 232),
('SG', 'Singapore', 'SGP', 65),
('SK', 'Slovakia', 'SVK', 421),
('SI', 'Slovenia', 'SVN', 386),
('SB', 'Solomon Islands', 'SLB', 677),
('SO', 'Somalia', 'SOM', 252),
('ZA', 'South Africa', 'ZAF', 27),
('GS', 'South Georgia and the South Sandwich Islands', NULL, 0),
('ES', 'Spain', 'ESP', 34),
('LK', 'Sri Lanka', 'LKA', 94),
('SD', 'Sudan', 'SDN', 249),
('SR', 'Suriname', 'SUR', 597),
('SJ', 'Svalbard and Jan Mayen', 'SJM', 47),
('SZ', 'Swaziland', 'SWZ', 268),
('SE', 'Sweden', 'SWE', 46),
('CH', 'Switzerland', 'CHE', 41),
('SY', 'Syrian Arab Republic', 'SYR', 963),
('TW', 'Taiwan, Province of China', 'TWN', 886),
('TJ', 'Tajikistan', 'TJK', 992),
('TZ', 'Tanzania, United Republic of', 'TZA', 255),
('TH', 'Thailand', 'THA', 66),
('TL', 'Timor-Leste', NULL, 670),
('TG', 'Togo', 'TGO', 228),
('TK', 'Tokelau', 'TKL', 690),
('TO', 'Tonga', 'TON', 676),
('TT', 'Trinidad and Tobago', 'TTO', 1868),
('TN', 'Tunisia', 'TUN', 216),
('TR', 'Turkey', 'TUR', 90),
('TM', 'Turkmenistan', 'TKM', 7370),
('TC', 'Turks and Caicos Islands', 'TCA', 1649),
('TV', 'Tuvalu', 'TUV', 688),
('UG', 'Uganda', 'UGA', 256),
('UA', 'Ukraine', 'UKR', 380),
('AE', 'United Arab Emirates', 'ARE', 971),
('GB', 'United Kingdom', 'GBR', 44),
('US', 'United States', 'USA', 1),
('UM', 'United States Minor Outlying Islands', NULL, 1),
('UY', 'Uruguay', 'URY', 598),
('UZ', 'Uzbekistan', 'UZB', 998),
('VU', 'Vanuatu', 'VUT', 678),
('VE', 'Venezuela', 'VEN', 58),
('VN', 'Viet Nam', 'VNM', 84),
('VG', 'Virgin Islands, British', 'VGB', 1284),
('VI', 'Virgin Islands, U.s.', 'VIR', 1340),
('WF', 'Wallis and Futuna', 'WLF', 681),
('EH', 'Western Sahara', 'ESH', 212),
('YE', 'Yemen', 'YEM', 967),
('ZM', 'Zambia', 'ZMB', 260),
('ZW', 'Zimbabwe', 'ZWE', 263);

-- --------------------------------------------------------

--
-- Table structure for table `set_location_state`
--

CREATE TABLE `set_location_state` (
  `StateName` varchar(255) NOT NULL,
  `CountryCode` char(2) NOT NULL,
  `Status` int(1) DEFAULT 2 COMMENT '1 for inactive 2 for active'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `set_location_state`
--

INSERT INTO `set_location_state` (`StateName`, `CountryCode`, `Status`) VALUES
('Andaman and Nicobar Islands', 'IN', 2),
('Andhra Pradesh', 'IN', 1),
('Arunachal Pradesh', 'IN', 2),
('Bihar', 'IN', 2),
('Chandigarh', 'IN', 2),
('Chhattisgarh', 'IN', 2),
('Dadra and Nagar Haveli', 'IN', 2),
('Daman and Diu', 'IN', 2),
('Delhi', 'IN', 2),
('Goa', 'IN', 2),
('Gujarat', 'IN', 2),
('Haryana', 'IN', 2),
('Himachal Pradesh', 'IN', 2),
('Jammu and Kashmir', 'IN', 2),
('Jharkhand', 'IN', 2),
('Karnataka', 'IN', 2),
('Kenmore', 'IN', 1),
('Kerala', 'IN', 2),
('Lakshadweep', 'IN', 2),
('Madhya Pradesh', 'IN', 2),
('Maharashtra', 'IN', 2),
('Manipur', 'IN', 2),
('Meghalaya', 'IN', 2),
('Mizoram', 'IN', 2),
('Nagaland', 'IN', 2),
('Narora', 'IN', 1),
('Natwar', 'IN', 1),
('Paschim Medinipur', 'IN', 1),
('Pondicherry', 'IN', 2),
('Punjab', 'IN', 2),
('Rajasthan', 'IN', 2),
('Sikkim', 'IN', 2),
('Tamil Nadu', 'IN', 1),
('Tripura', 'IN', 2),
('Uttar Pradesh', 'IN', 2),
('Uttarakhand', 'IN', 2),
('Vaishali', 'IN', 1),
('West Bengal', 'IN', 2);

-- --------------------------------------------------------

--
-- Table structure for table `set_pages`
--

CREATE TABLE `set_pages` (
  `PageID` int(11) NOT NULL,
  `PageGUID` varchar(36) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `Content` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `set_site_config`
--

CREATE TABLE `set_site_config` (
  `ConfigTypeGUID` varchar(50) NOT NULL,
  `ConfigTypeDescprition` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
  `ConfigTypeValue` varchar(255) CHARACTER SET utf8 NOT NULL,
  `StatusID` int(11) NOT NULL DEFAULT 2,
  `Sort` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `set_site_config`
--

INSERT INTO `set_site_config` (`ConfigTypeGUID`, `ConfigTypeDescprition`, `ConfigTypeValue`, `StatusID`, `Sort`) VALUES
('AndridAppUrl', 'Andrid App Url', 'https://www.myskills11.com/android/MySkills11.apk', 2, 0),
('AndroidAppFeatures', 'Android App Features', '<p>1) New Flexible Confirmed Leagues</p>\n<p>2) Star Icon For Star Members Teams</p>', 2, 10),
('AndroidAppVersion', 'Android App Version', '15', 2, 0),
('CashBonusExpireTimeInDays', 'Cashbonus expire in days', '180', 2, 12),
('DepositBonusStarMember', 'Deposit Bonus Star Member', '100', 2, 3),
('FirstDepositBonus', 'First Deposit Bonus', '100', 2, 2),
('IsAndroidAppUpdateMandatory', 'Is Android App Update Mandatory', 'Yes', 2, 0),
('LuckyWheel', 'Lucky Wheel Status', '0', 6, 7),
('LuckyWheelTime', 'Lucky Wheel Time', '0', 6, 0),
('MatchLiveTime', 'Match On Going Live In Minutes', '0', 2, 8),
('MaximumFirstTimeDepositLimit', 'First Deposit Bonus Maximum Amount', '10000', 6, 2),
('MaximumWithdrawalLimitBank', 'Per Day Maximum Withdrawal Limit Bank', '10000', 2, 0),
('MaximumWithdrawalLimitPaytm', 'Per Day Maximum Withdrawal Limit Paytm', '1000', 2, 0),
('MinimumDepositLimit', 'Minimum Deposit Limit', '1', 2, 3),
('MinimumFirstTimeDepositLimit', 'First Deposit Bonus Minimum Amount', '1', 6, 2),
('MinimumWithdrawalLimitBank', 'Minimum Withdrawal Limit Bank', '200', 2, 0),
('MinimumWithdrawalLimitPaytm', 'Minimum Withdrawal Limit Paytm', '10', 2, 0),
('MlmFirstLevel', 'MLM first level percentage', '2.5', 6, 9),
('MlmIsActive', 'MLM is active', 'Yes', 6, 8),
('MlmSecondLevel', 'MLM second level percentage', '1.5', 6, 10),
('MlmThirdLevel', 'MLM third level percentage', '1', 6, 11),
('ReferByDepositBonus', 'Refer By Deposit Bonus', '100', 2, 4),
('ReferToDepositBonus', 'Refer To Deposit Bonus', '100', 2, 5),
('SandGridApi', 'SandGrid Api ', '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', 2, 3),
('SendGridApi', 'Sandgrid API Key', '', 2, 7),
('SignupBonus', 'Signup Bonus', '100', 2, 6),
('SignupWalletDepositBonus', 'Signup Direct Deposit Amount', '10', 6, 6),
('TotalWheelAmount', 'Total Wheel Amount', '0', 6, 0),
('VerificationBonus', 'Verification Bonus', '50', 2, 7);

-- --------------------------------------------------------

--
-- Table structure for table `set_source`
--

CREATE TABLE `set_source` (
  `SourceID` int(11) NOT NULL,
  `SourceName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `set_source`
--

INSERT INTO `set_source` (`SourceID`, `SourceName`) VALUES
(1, 'Direct'),
(2, 'Facebook'),
(3, 'Twitter'),
(4, 'Google'),
(5, 'LinkedIn'),
(6, 'Otp'),
(7, 'SignInWithApple');

-- --------------------------------------------------------

--
-- Table structure for table `set_status`
--

CREATE TABLE `set_status` (
  `StatusID` int(11) NOT NULL,
  `StatusName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `set_status`
--

INSERT INTO `set_status` (`StatusID`, `StatusName`) VALUES
(1, 'Pending,Upcoming'),
(2, 'Verified,Active,Running,Processing'),
(3, 'Deleted,Rejected,Cancelled,Failed'),
(4, 'Blocked,Closed'),
(5, 'Delivered,Completed'),
(6, 'Deactive,Inactive'),
(7, 'Discontinued'),
(8, 'Abandoned'),
(9, 'No Result, Not Submitted'),
(10, 'Match Review,Reviewing');

-- --------------------------------------------------------

--
-- Table structure for table `set_virtual_names`
--

CREATE TABLE `set_virtual_names` (
  `ID` int(11) NOT NULL,
  `Names` varchar(255) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `social_post`
--

CREATE TABLE `social_post` (
  `PostID` int(11) NOT NULL,
  `PostGUID` varchar(36) NOT NULL,
  `ParentPostID` int(11) DEFAULT NULL,
  `PostType` enum('Activity','Comment','Review','Question','Testimonial') DEFAULT NULL,
  `EntityID` int(11) NOT NULL,
  `ToEntityID` int(11) NOT NULL,
  `PostCaption` mediumtext DEFAULT NULL COMMENT 'Title',
  `PostContent` mediumtext DEFAULT NULL,
  `Privacy` enum('Public','Private','Friends') NOT NULL DEFAULT 'Public',
  `Sort` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `social_subscribers`
--

CREATE TABLE `social_subscribers` (
  `UserID` int(11) NOT NULL COMMENT 'Requested by User',
  `ToEntityID` int(11) NOT NULL COMMENT 'Can be ID of User, Page, Group etc.',
  `Action` enum('Friend','Follow','Subscribe') NOT NULL DEFAULT 'Follow',
  `EntryDate` datetime NOT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  `StatusID` int(11) NOT NULL DEFAULT 1,
  `IsAdmin` enum('Yes','No') NOT NULL DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sports_announcement`
--

CREATE TABLE `sports_announcement` (
  `ID` int(11) NOT NULL,
  `Message` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_auction_draft_player_point`
--

CREATE TABLE `sports_auction_draft_player_point` (
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `ContestID` int(11) DEFAULT NULL,
  `PlayerID` int(11) NOT NULL,
  `PlayerRole` enum('AllRounder','Batsman','Bowler','WicketKeeper','Other') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Other',
  `TotalPoints` decimal(10,0) NOT NULL DEFAULT 0,
  `UpdateDateTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sports_auction_draft_series_players`
--

CREATE TABLE `sports_auction_draft_series_players` (
  `PlayerID` int(11) NOT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `TeamID` int(11) NOT NULL,
  `PlayerRole` enum('AllRounder','Batsman','Bowler','WicketKeeper','Other') NOT NULL,
  `PlayerSalary` float(8,2) NOT NULL DEFAULT 0.00,
  `IsAdminUpdate` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsActive` enum('Yes','No') NOT NULL DEFAULT 'Yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_contest`
--

CREATE TABLE `sports_contest` (
  `ContestID` int(11) NOT NULL,
  `ContestGUID` varchar(36) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `LeagueType` enum('Dfs','Draft','Auction','') NOT NULL DEFAULT 'Dfs',
  `GameType` enum('Advance','Safe') NOT NULL DEFAULT 'Advance',
  `GameTimeLive` smallint(6) DEFAULT 0,
  `LeagueJoinDateTime` datetime DEFAULT NULL,
  `AdminPercent` smallint(6) DEFAULT NULL,
  `ContestFormat` enum('Head to Head','League') NOT NULL,
  `ContestType` enum('Normal','Reverse','InPlay','Hot','Champion','Practice','More','Mega','Winner Takes All','Only For Beginners','Head to Head','Infinity Pool','Smart Pool') NOT NULL,
  `IsAutoCreate` enum('No','Yes') NOT NULL DEFAULT 'No',
  `PreContestID` int(11) DEFAULT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `ContestName` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
  `Privacy` enum('Yes','No') NOT NULL,
  `IsPaid` enum('Yes','No') NOT NULL,
  `IsConfirm` enum('Yes','No') NOT NULL DEFAULT 'No',
  `ShowJoinedContest` enum('Yes','No') NOT NULL,
  `WinningAmount` int(11) NOT NULL DEFAULT 0,
  `ContestSize` int(11) NOT NULL,
  `TotalJoinedTeams` int(11) NOT NULL DEFAULT 0,
  `CashBonusContribution` float(6,2) NOT NULL,
  `UserJoinLimit` int(11) NOT NULL DEFAULT 1,
  `EntryType` enum('Single','Multiple') NOT NULL,
  `EntryFee` int(11) NOT NULL,
  `ContestContribution` double DEFAULT 0,
  `NoOfWinners` int(11) NOT NULL,
  `CustomizeWinning` text DEFAULT NULL,
  `UnfilledWinningPercent` enum('Fixed','GuranteedPool') NOT NULL DEFAULT 'Fixed',
  `WinningRatio` int(11) NOT NULL DEFAULT 0,
  `WinUpTo` float(4,2) NOT NULL DEFAULT 0.00,
  `UserInvitationCode` varchar(50) DEFAULT NULL,
  `IsWinningDistributed` enum('No','Yes') NOT NULL DEFAULT 'No',
  `MinimumUserJoined` int(11) DEFAULT NULL,
  `AuctionStatusID` int(11) NOT NULL DEFAULT 1,
  `AuctionUpdateTime` datetime DEFAULT NULL,
  `AuctionTimeBreakAvailable` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionIsBreakTimeStatus` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionBreakDateTime` datetime DEFAULT NULL,
  `DraftTotalRounds` smallint(6) DEFAULT NULL,
  `DraftLiveRound` smallint(6) DEFAULT NULL,
  `DraftUserTeamSubmitted` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsRefund` enum('Yes','No') NOT NULL DEFAULT 'No',
  `SmartPool` enum('Yes','No') NOT NULL DEFAULT 'No',
  `SmartPoolText` varchar(50) DEFAULT NULL,
  `IsWinningDistribute` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistributeAmount` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningSetNotFillContest` enum('Yes','No') NOT NULL DEFAULT 'No' COMMENT 'set custom winning for not filled confirm contest',
  `ContestTransferred` enum('No','Yes') NOT NULL DEFAULT 'No',
  `isMailSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsVirtualUserJoined` enum('Yes','No','Completed') NOT NULL DEFAULT 'No',
  `IsDummyJoined` tinyint(4) NOT NULL DEFAULT 0,
  `VirtualUserJoinedPercentage` int(6) DEFAULT NULL,
  `DraftTeamPlayerLimit` smallint(6) DEFAULT NULL,
  `DraftPlayerSelectionCriteria` varchar(255) DEFAULT NULL,
  `IsVirtualTeamCreated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsPrivacyNameDisplay` enum('Yes','No') DEFAULT 'No',
  `CancelledBy` enum('Admin','Cron') DEFAULT 'Cron',
  `IsContestBackup` enum('Yes','No') NOT NULL DEFAULT 'No',
  `WinningType` enum('Free Join Contest','Paid Join Contest') NOT NULL DEFAULT 'Paid Join Contest',
  `NewNoOfWinners` int(11) NOT NULL,
  `WinningPercent` int(11) DEFAULT NULL,
  `DynamicCustomizeWinning` text DEFAULT NULL,
  `NewWinningAmount` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_contest_completed`
--

CREATE TABLE `sports_contest_completed` (
  `ContestID` int(11) NOT NULL,
  `ContestGUID` varchar(36) NOT NULL,
  `UserID` int(11) NOT NULL,
  `LeagueType` enum('Dfs','Draft','Auction','') NOT NULL DEFAULT 'Dfs',
  `GameType` enum('Advance','Safe') NOT NULL DEFAULT 'Advance',
  `GameTimeLive` smallint(6) DEFAULT 0,
  `LeagueJoinDateTime` datetime DEFAULT NULL,
  `AdminPercent` smallint(6) DEFAULT NULL,
  `ContestFormat` enum('Head to Head','League') NOT NULL,
  `ContestType` enum('Normal','Reverse','InPlay','Hot','Champion','Practice','More','Mega','Winner Takes All','Only For Beginners','Head to Head','Infinity Pool','Smart Pool') NOT NULL,
  `IsAutoCreate` enum('No','Yes') NOT NULL DEFAULT 'No',
  `PreContestID` int(11) DEFAULT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `ContestName` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
  `Privacy` enum('Yes','No') NOT NULL,
  `IsPaid` enum('Yes','No') NOT NULL,
  `IsConfirm` enum('Yes','No') NOT NULL DEFAULT 'No',
  `ShowJoinedContest` enum('Yes','No') NOT NULL,
  `WinningAmount` int(11) NOT NULL DEFAULT 0,
  `ContestSize` int(11) NOT NULL,
  `TotalJoinedTeams` int(11) NOT NULL DEFAULT 0,
  `CashBonusContribution` float(6,2) NOT NULL,
  `UserJoinLimit` int(11) NOT NULL DEFAULT 1,
  `EntryType` enum('Single','Multiple') NOT NULL,
  `EntryFee` int(11) NOT NULL,
  `NoOfWinners` int(11) NOT NULL,
  `CustomizeWinning` text DEFAULT NULL,
  `UnfilledWinningPercent` enum('Fixed','GuranteedPool') NOT NULL DEFAULT 'Fixed',
  `WinningRatio` int(11) NOT NULL DEFAULT 0,
  `WinUpTo` float(4,2) NOT NULL DEFAULT 0.00,
  `UserInvitationCode` varchar(50) DEFAULT NULL,
  `IsWinningDistributed` enum('No','Yes') NOT NULL DEFAULT 'No',
  `MinimumUserJoined` int(11) DEFAULT NULL,
  `AuctionStatusID` int(11) NOT NULL DEFAULT 1,
  `AuctionUpdateTime` datetime DEFAULT NULL,
  `AuctionTimeBreakAvailable` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionIsBreakTimeStatus` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionBreakDateTime` datetime DEFAULT NULL,
  `DraftTotalRounds` smallint(6) DEFAULT NULL,
  `DraftLiveRound` smallint(6) DEFAULT NULL,
  `DraftUserTeamSubmitted` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsRefund` enum('Yes','No') NOT NULL DEFAULT 'No',
  `SmartPool` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistribute` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistributeAmount` enum('Yes','No') NOT NULL DEFAULT 'No',
  `ContestTransferred` enum('No','Yes') NOT NULL DEFAULT 'No',
  `isMailSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsVirtualUserJoined` enum('Yes','No','Completed') NOT NULL DEFAULT 'No',
  `IsDummyJoined` tinyint(4) NOT NULL DEFAULT 0,
  `VirtualUserJoinedPercentage` int(6) DEFAULT NULL,
  `DraftTeamPlayerLimit` smallint(6) DEFAULT NULL,
  `DraftPlayerSelectionCriteria` varchar(255) DEFAULT NULL,
  `IsVirtualTeamCreated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsPrivacyNameDisplay` enum('Yes','No') DEFAULT 'No',
  `CancelledBy` enum('Admin','Cron') DEFAULT 'Cron',
  `WinningType` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_contest_join`
--

CREATE TABLE `sports_contest_join` (
  `ContestID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `SeriesID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `JoinInning` enum('First','Second','Third','Fourth') DEFAULT NULL,
  `SmartPool` enum('Yes','No') NOT NULL DEFAULT 'No',
  `SmartPoolWinning` varchar(255) DEFAULT NULL,
  `UserTeamID` int(11) DEFAULT NULL,
  `JoinWalletAmount` float(8,2) DEFAULT 0.00,
  `JoinWinningAmount` float(8,2) NOT NULL DEFAULT 0.00,
  `JoinCashBonus` float(8,2) NOT NULL DEFAULT 0.00,
  `TotalPoints` float(8,2) NOT NULL,
  `UserRank` int(11) DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  `UserWinningAmount` float(8,2) NOT NULL,
  `SubscriberContribution` double DEFAULT 0,
  `EntryDate` datetime NOT NULL,
  `AuctionTimeBank` smallint(6) NOT NULL DEFAULT 180,
  `AuctionBudget` bigint(20) NOT NULL DEFAULT 1000000000,
  `AuctionUserStatus` enum('Online','Offline') NOT NULL DEFAULT 'Offline',
  `IsHold` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionHoldDateTime` datetime DEFAULT NULL,
  `DraftUserPosition` smallint(6) DEFAULT NULL,
  `DraftUserLive` enum('Yes','No') NOT NULL DEFAULT 'No',
  `DraftUserLiveTime` datetime DEFAULT NULL,
  `IsRefund` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistribute` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistributeAmount` enum('Yes','No') NOT NULL DEFAULT 'No',
  `isMailSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `CopiedTeam` int(11) NOT NULL DEFAULT 0,
  `NotificationAlert` enum('1440','60','15','5','None') DEFAULT 'None',
  `IsSubscribe` enum('No','Yes') DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_contest_join_completed`
--

CREATE TABLE `sports_contest_join_completed` (
  `ContestID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `SeriesID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `JoinInning` enum('First','Second','Third','Fourth') DEFAULT NULL,
  `SmartPool` enum('Yes','No') NOT NULL DEFAULT 'No',
  `SmartPoolWinning` varchar(255) DEFAULT NULL,
  `UserTeamID` int(11) DEFAULT NULL,
  `JoinWalletAmount` float(8,2) DEFAULT 0.00,
  `JoinWinningAmount` float(8,2) NOT NULL DEFAULT 0.00,
  `JoinCashBonus` float(8,2) NOT NULL DEFAULT 0.00,
  `TotalPoints` float(8,2) NOT NULL,
  `UserRank` int(11) DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  `UserWinningAmount` float(8,2) NOT NULL,
  `EntryDate` datetime NOT NULL,
  `AuctionTimeBank` smallint(6) NOT NULL DEFAULT 180,
  `AuctionBudget` bigint(20) NOT NULL DEFAULT 1000000000,
  `AuctionUserStatus` enum('Online','Offline') NOT NULL DEFAULT 'Offline',
  `IsHold` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionHoldDateTime` datetime DEFAULT NULL,
  `DraftUserPosition` smallint(6) DEFAULT NULL,
  `DraftUserLive` enum('Yes','No') NOT NULL DEFAULT 'No',
  `DraftUserLiveTime` datetime DEFAULT NULL,
  `IsRefund` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistribute` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsWinningDistributeAmount` enum('Yes','No') NOT NULL DEFAULT 'No',
  `isMailSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `CopiedTeam` int(11) NOT NULL DEFAULT 0,
  `NotificationAlert` enum('1440','60','15','5','None') DEFAULT 'None',
  `WinningType` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_matches`
--

CREATE TABLE `sports_matches` (
  `MatchID` int(11) NOT NULL,
  `MatchGUID` char(36) NOT NULL,
  `MatchIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `MatchTypeID` int(11) NOT NULL,
  `MatchNo` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `MatchLocation` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
  `TeamIDLocal` int(11) NOT NULL,
  `TeamIDVisitor` int(11) NOT NULL,
  `LocalSeasonTeamKey` varchar(150) DEFAULT NULL,
  `VisitorSeasonTeamKey` varchar(150) DEFAULT NULL,
  `MatchStartDateTime` datetime DEFAULT NULL,
  `APIAutoTimeUpdate` enum('Yes','No') NOT NULL DEFAULT 'No',
  `MatchClosedInMinutes` smallint(6) DEFAULT NULL,
  `MatchCompleteDateTime` datetime DEFAULT NULL,
  `IsPreSquad` enum('Yes','No') NOT NULL DEFAULT 'No' COMMENT 'if yes matches going to user crate team ',
  `PlayerStatsUpdate` enum('Yes','No') NOT NULL DEFAULT 'No',
  `LivePlayerStatusUpdate` enum('No','Yes') NOT NULL DEFAULT 'No',
  `PointsLastUpdatedOn` datetime DEFAULT NULL,
  `MatchScoreDetails` text DEFAULT NULL,
  `LastUpdatedOn` datetime DEFAULT NULL,
  `IsPlayingXINotificationSent` enum('No','Yes') NOT NULL DEFAULT 'No',
  `isReminderNotificationSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsPlayerPointsUpdated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsUserPointsUpdated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `PlayingNotifyTime` datetime DEFAULT NULL,
  `isMailSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsBackupDone` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsUserBackupDone` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsContestBackup` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsBackupDeleted` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AdminMsgSent` enum('No','Yes') DEFAULT 'No',
  `TossDelayIsSent` enum('Yes','No') NOT NULL DEFAULT 'No',
  `ApiType` enum('MG100-MG100B','MG101') NOT NULL DEFAULT 'MG100-MG100B',
  `MatchDisplay` enum('Enable','Disable') DEFAULT 'Disable',
  `AdminChangeRole` enum('No','Yes') DEFAULT 'No',
  `MatchTypeByApi` enum('Real','Virtual') NOT NULL DEFAULT 'Real',
  `IsEdited` enum('Yes','No') NOT NULL DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_players`
--

CREATE TABLE `sports_players` (
  `PlayerID` int(11) NOT NULL,
  `PlayerGUID` varchar(36) NOT NULL,
  `PlayerIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `PlayerName` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `PlayerPic` varchar(50) DEFAULT NULL,
  `PlayerCountry` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `PlayerBattingStyle` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `PlayerBowlingStyle` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `PlayerBattingStats` text DEFAULT NULL,
  `PlayerBowlingStats` text DEFAULT NULL,
  `PlayerFieldingStats` text NOT NULL,
  `PlayerSalary` float(4,2) DEFAULT NULL,
  `IsAdminSalaryUpdated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `LastUpdatedOn` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_pre_contest`
--

CREATE TABLE `sports_pre_contest` (
  `PreContestID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `SeriesIDs` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `AllSeries` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `LeagueType` enum('Dfs','Draft','Auction') COLLATE utf8_unicode_ci DEFAULT 'Dfs',
  `ContestFormat` enum('Head to Head','League') COLLATE utf8_unicode_ci NOT NULL,
  `ContestType` enum('Normal','Reverse','InPlay','Hot','Champion','Practice','More','Mega','Winner Takes All','Only For Beginners','Head to Head','Smart Pool','Infinity Pool') COLLATE utf8_unicode_ci NOT NULL,
  `ContestName` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `Privacy` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL,
  `IsPaid` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL,
  `IsConfirm` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Yes',
  `IsAutoCreate` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `ShowJoinedContest` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL,
  `WinningAmount` int(11) NOT NULL DEFAULT 0,
  `ContestSize` int(11) NOT NULL,
  `UnfilledWinningPercent` enum('Fixed','GuranteedPool') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Fixed',
  `WinningRatio` int(11) NOT NULL DEFAULT 0,
  `WinUpTo` float(4,2) NOT NULL DEFAULT 0.00,
  `SmartPool` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `SmartPoolText` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CashBonusContribution` float(6,2) NOT NULL,
  `AdminPercent` float(6,2) NOT NULL,
  `UserJoinLimit` int(11) NOT NULL DEFAULT 6,
  `EntryType` enum('Single','Multiple') COLLATE utf8_unicode_ci NOT NULL,
  `EntryFee` int(11) NOT NULL,
  `ContestContribution` double DEFAULT 0,
  `NoOfWinners` int(11) NOT NULL,
  `CustomizeWinning` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `IsWinnerSocialFeed` enum('No','Yes') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No' COMMENT '(If Yes, then after contest winning distribution we will share contest winner information on facebook page )',
  `IsWinningDistributed` enum('No','Yes') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `IsVirtualUserJoined` enum('Yes','No') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'No',
  `DraftTeamPlayerLimit` int(11) DEFAULT NULL,
  `VirtualUserJoinedPercentage` int(6) NOT NULL DEFAULT 0,
  `DraftPlayerSelectionCriteria` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `StatusID` int(1) DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sports_series`
--

CREATE TABLE `sports_series` (
  `SeriesID` int(11) NOT NULL,
  `SeriesGUID` char(36) NOT NULL,
  `SeriesIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `SeriesName` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `SeriesType` varchar(50) DEFAULT NULL,
  `SeriesStartDate` date DEFAULT NULL,
  `SeriesEndDate` date DEFAULT NULL,
  `AuctionDraftIsPlayed` enum('Yes','No') NOT NULL DEFAULT 'Yes',
  `DraftUserLimit` int(11) NOT NULL DEFAULT 0 COMMENT 'Snake & Auction Draft',
  `DraftTeamPlayerLimit` smallint(6) DEFAULT NULL,
  `DraftPlayerSelectionCriteria` varchar(255) DEFAULT NULL,
  `AuctionDraftStatusID` int(11) NOT NULL DEFAULT 1,
  `CompetitionStateKey` varchar(50) DEFAULT NULL,
  `SeriesTypeByApi` enum('MG100-MG100B','MG101') NOT NULL DEFAULT 'MG100-MG100B'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_series_rounds`
--

CREATE TABLE `sports_series_rounds` (
  `RoundID` int(11) NOT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `RoundName` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `SeriesType` varchar(50) DEFAULT NULL,
  `RoundFormat` varchar(50) DEFAULT NULL,
  `RoundStartDate` date DEFAULT NULL,
  `RoundEndDate` date DEFAULT NULL,
  `AuctionDraftIsPlayed` enum('Yes','No') NOT NULL DEFAULT 'Yes',
  `DraftUserLimit` int(11) NOT NULL DEFAULT 0 COMMENT 'Snake & Auction Draft',
  `DraftTeamPlayerLimit` smallint(6) DEFAULT NULL,
  `DraftPlayerSelectionCriteria` varchar(255) DEFAULT NULL,
  `AuctionDraftStatusID` int(11) NOT NULL DEFAULT 1,
  `StatusID` int(11) NOT NULL DEFAULT 1,
  `SeriesDisplay` enum('Enable','Disable') DEFAULT 'Enable'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_setting_points`
--

CREATE TABLE `sports_setting_points` (
  `PointsTypeGUID` varchar(36) NOT NULL,
  `StaticDescription` varchar(255) DEFAULT NULL,
  `PointsTypeDescprition` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `PointsTypeShortDescription` char(22) NOT NULL,
  `PointsType` enum('General Point','Bonus Point','Economy Rate','Strike Rate') NOT NULL,
  `PointsInningType` enum('Batting','Bowling','Fielding') NOT NULL,
  `PointsScoringField` varchar(50) DEFAULT NULL,
  `PointsT20` float(4,1) NOT NULL DEFAULT 0.0,
  `PointsT10` float(4,1) NOT NULL DEFAULT 0.0,
  `PointsODI` float(4,1) NOT NULL DEFAULT 0.0,
  `PointsTEST` float(4,1) NOT NULL DEFAULT 0.0,
  `PointsT20InPlay` float(4,1) NOT NULL DEFAULT 0.0,
  `PointsODIInPlay` float(4,1) NOT NULL DEFAULT 0.0,
  `PointsTESTInPlay` float(4,1) NOT NULL DEFAULT 0.0,
  `PointsT20Reverse` float(4,1) NOT NULL DEFAULT 0.0,
  `PointsODIReverse` float(4,1) NOT NULL DEFAULT 0.0,
  `PointsTESTReverse` float(4,1) NOT NULL DEFAULT 0.0,
  `StatusID` int(11) NOT NULL DEFAULT 6,
  `Sort` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sports_setting_points`
--

INSERT INTO `sports_setting_points` (`PointsTypeGUID`, `StaticDescription`, `PointsTypeDescprition`, `PointsTypeShortDescription`, `PointsType`, `PointsInningType`, `PointsScoringField`, `PointsT20`, `PointsT10`, `PointsODI`, `PointsTEST`, `PointsT20InPlay`, `PointsODIInPlay`, `PointsTESTInPlay`, `PointsT20Reverse`, `PointsODIReverse`, `PointsTESTReverse`, `StatusID`, `Sort`) VALUES
('BattingMinimumRuns', 'Applicable for players batting minimum runs', 'Applicable for players batting minimum runs', '', 'Strike Rate', 'Batting', NULL, 15.0, 20.0, 15.0, 15.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 6, 17),
('CaptainPointMP', 'Captain points Multiplier', 'Captain points Multiplier', '', 'General Point', 'Batting', NULL, 2.0, 2.0, 2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 18),
('Catch', 'Catch', 'Catch', 'CT', 'General Point', 'Fielding', 'Catches', 8.0, 8.0, 8.0, 8.0, 0.0, 0.0, 0.0, 10.0, 10.0, 10.0, 1, 1),
('DotBall', 'Dot Ball', 'Dot Ball', 'DotBall', 'Bonus Point', 'Bowling', 'DotBall', 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, 0.0, 1, 6),
('Duck', 'Duck (EXCEPT BOWLER)', 'Duck (EXCEPT BOWLER)', 'DUCK', 'General Point', 'Batting', 'Runs', -2.0, -2.0, -3.0, -4.0, 0.0, 0.0, 0.0, -5.0, -5.0, -5.0, 1, 10),
('EconomyRate0N5Balls', 'Economy rate 0 to 5.00', 'Economy rate 0 to 3.99', 'EB', 'Economy Rate', 'Bowling', 'Economy', 6.0, 8.0, 6.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 9),
('EconomyRate10.01N12.00Balls', 'Economy rate 10.01 to 12.00', 'Economy rate 10.01 to 11.00', 'EB', 'Economy Rate', 'Bowling', 'Economy', -4.0, -4.0, -6.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 14),
('EconomyRate5.01N7.00Balls', 'Economy rate 5.01 to 7.00', 'Economy rate 4.00 to 4.99', 'EB', 'Economy Rate', 'Bowling', 'Economy', 4.0, 6.0, 2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 10),
('EconomyRate5.01N8.00Balls', 'Economy rate 5.01 to 8.00', 'Economy rate 0 to 2.49', 'EB', 'Economy Rate', 'Bowling', 'Economy', 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 11),
('EconomyRate7.01N10.00Balls', 'Economy rate 7.01 to 10.00', 'Economy rate 5.00 to 6.00', 'EB', 'Economy Rate', 'Bowling', 'Economy', 2.0, 2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 12),
('EconomyRate8.01N10.00Balls', 'Economy rate 8.01 to 10.00', 'Economy rate 9.00 to 10.00', 'EB', 'Economy Rate', 'Bowling', 'Economy', -2.0, -2.0, -4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 13),
('EconomyRateAbove12.1Balls', 'Economy rate 12.01 or more', 'Economy rate 11.01 or more', 'EB', 'Economy Rate', 'Bowling', 'Economy', -6.0, -6.0, -8.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 15),
('EightWicketsMore', 'For 8 wickets or more', 'For 8 wickets or more', 'BWB', 'Bonus Point', 'Bowling', 'Wickets', 30.0, 40.0, 20.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 7),
('EveryRunScored', 'For every run scored', 'For every run scored', 'RUNS', 'General Point', 'Batting', 'Runs', 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1, 1),
('FiveWickets', 'For 5-wicket', 'For 5-wicket', 'BWB', 'Bonus Point', 'Bowling', 'Wickets', 16.0, 25.0, 8.0, 8.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 4),
('For100runs', 'For 100 runs', 'For 100 runs', 'BTB', 'General Point', 'Batting', 'Runs', 16.0, 20.0, 8.0, 8.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 6),
('For150runs', 'For 150 runs', 'For 150 runs', 'BTB', 'General Point', 'Batting', 'Runs', 0.0, 0.0, 30.0, 25.0, 0.0, 0.0, 0.0, 10.0, 30.0, 25.0, 1, 7),
('For200runs', 'For 200 runs', 'For 200 runs', 'BTB', 'General Point', 'Batting', 'Runs', 0.0, 0.0, 40.0, 30.0, 0.0, 0.0, 0.0, 0.0, 40.0, 30.0, 1, 8),
('For300runs', 'For 300 runs or more', 'For 300 runs or more', 'BTB', 'General Point', 'Batting', 'Runs', 0.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 9),
('For30runs', 'For 30 runs', 'For 30 runs', 'BTB', 'General Point', 'Batting', 'Runs', 6.0, 8.0, 0.0, 0.0, 0.0, 0.0, 0.0, -5.0, 0.0, 0.0, 1, 5),
('For50runs', 'For 50 runs', 'For 50 runs', 'BTB', 'General Point', 'Batting', 'Runs', 8.0, 16.0, 4.0, 4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 5),
('Four', 'For every 4 hit', 'For every 4 hit', '4s', 'General Point', 'Batting', 'Fours', 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 2),
('FourWickets', 'For 4-wicket', 'For 4-wicket', 'BWB', 'Bonus Point', 'Bowling', 'Wickets', 8.0, 20.0, 4.0, 4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 3),
('Maiden', 'For every maiden', 'For every maiden', 'MD', 'Bonus Point', 'Bowling', 'Maidens', 8.0, 16.0, 4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 8),
('MinimumBallsScoreStrikeRate', 'A player must score a minimum balls to be awarded the Strike Rate\nbonus (EXCEPT BOWLER)', 'A player must score a minimum balls to be awarded the Strike Rate bonus (EXCEPT BOWLER)', '', 'Strike Rate', 'Batting', NULL, 10.0, 5.0, 20.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 2.0, 1, 1),
('MinimumOverEconomyRate', 'A player must bowl a minimum over to be awarded the Economy Rate bonus', 'A player must bowl a minimum over to be awarded the Economy Rate bonus', '', 'Economy Rate', 'Bowling', NULL, 2.0, 1.0, 5.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 2.0, 1, 1),
('MinimumRunScoreStrikeRate', 'A player must score a minimum runs to be awarded the Strike Rate\nbonus', 'A player must score a minimum runs to be awarded the Strike Rate\nbonus', '', 'Strike Rate', 'Batting', NULL, 15.0, 20.0, 15.0, 15.0, 0.0, 0.0, 0.0, 2.0, 0.0, 2.0, 6, 18),
('NoBall', 'No Ball', 'No Ball', 'NoBall', 'Bonus Point', 'Bowling', 'NoBall', -2.0, -2.0, -2.0, 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, 0.0, 1, 7),
('RunOUT', 'Run-out', 'Run-out', 'RO', 'General Point', 'Fielding', 'RunOutDirectHit', 12.0, 12.0, 12.0, 12.0, 0.0, 0.0, 0.0, 0.0, 10.0, 10.0, 1, 3),
('SevenWicketsMore', 'For 7 wickets', 'For 7 wickets', 'BWB', 'Bonus Point', 'Bowling', 'Wickets', 25.0, 35.0, 16.0, 16.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 6),
('Six', 'For every 6 hit', 'For every 6 hit', '6s', 'General Point', 'Batting', 'Sixes', 2.0, 2.0, 2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 3),
('SixWickets', 'For 6-wicket', 'For 6-wicket', 'BWB', 'Bonus Point', 'Bowling', 'Wickets', 20.0, 30.0, 12.0, 12.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 5),
('StatringXI', 'For being part of the starting XI', 'For being part of the starting XI', 'SB', 'General Point', 'Batting', NULL, 4.0, 4.0, 4.0, 4.0, 0.0, 0.0, 0.0, 2.0, 0.0, 2.0, 1, 20),
('StrikeRate0N49.99', 'Strike rate 0 to 49.99', 'Strike rate 0 to 49.99', 'STB', 'Strike Rate', 'Batting', 'StrikeRate', -6.0, 0.0, -4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 11),
('StrikeRate100N149.99', 'Strike rate 100 to 149.99', 'Strike rate 80.00 to 89.99', 'STB', 'Strike Rate', 'Batting', 'StrikeRate', 0.0, -4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 14),
('StrikeRate150N199.99', 'Strike rate 150.00 to 199.99', 'Strike rate 90.00 to 99.99', 'STB', 'Strike Rate', 'Batting', 'StrikeRate', 0.0, -2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 15),
('StrikeRate200NMore', 'Strike rate 200.00 or more', 'Strike rate below 80.00', 'STB', 'Strike Rate', 'Batting', 'StrikeRate', 0.0, -6.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 16),
('StrikeRate50N74.99', 'Strike rate 50 to 74.99', 'Strike rate 50.00 to 59.99', 'STB', 'Strike Rate', 'Batting', 'StrikeRate', -4.0, 0.0, -2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 12),
('StrikeRate75N99.99', 'Strike rate 75.00 to 99.99', 'Strike rate 60.00 to 70.00', 'STB', 'Strike Rate', 'Batting', 'StrikeRate', -2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 13),
('Stumping', 'Stumping', 'Stumping', 'ST', 'General Point', 'Fielding', 'Stumping', 12.0, 12.0, 12.0, 12.0, 0.0, 0.0, 0.0, 15.0, 15.0, 15.0, 1, 2),
('ThreeWickets', 'For 3-wicket', 'For 3-wicket', 'BWB', 'Bonus Point', 'Bowling', 'Wickets', 6.0, 16.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 2),
('ViceCaptainPointMP', 'Vice captain points multiplier', 'Vice captain points multiplier', '', 'General Point', 'Batting', NULL, 1.5, 1.5, 1.5, 1.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1, 19),
('Wicket', 'For every wicket taken', 'For every wicket taken', 'WK', 'General Point', 'Bowling', 'Wickets', 25.0, 25.0, 25.0, 20.0, 0.0, 0.0, 0.0, 10.0, 15.1, 20.0, 1, 2),
('WideBall', 'Wide Ball', 'Wide Ball', 'WideBall', 'Bonus Point', 'Bowling', 'WideBall', -1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, 0.0, 1, 8);

-- --------------------------------------------------------

--
-- Table structure for table `sports_set_match_types`
--

CREATE TABLE `sports_set_match_types` (
  `MatchTypeID` int(11) NOT NULL,
  `MatchTypeName` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '(Entity API)',
  `MatchTypeNameCricketAPI` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sports_set_match_types`
--

INSERT INTO `sports_set_match_types` (`MatchTypeID`, `MatchTypeName`, `MatchTypeNameCricketAPI`) VALUES
(1, 'ODI', 'one-day'),
(2, 'First Class', NULL),
(3, 'T20', 't20'),
(4, 'T20I', NULL),
(5, 'Test', 'test'),
(6, 'Others', NULL),
(7, 'Woman T20', NULL),
(8, 'List A', NULL),
(9, 'Woman ODI', NULL),
(10, 'Youth ODI', NULL),
(11, 'Youth T20', NULL),
(12, 'T10', 't10');

-- --------------------------------------------------------

--
-- Table structure for table `sports_teams`
--

CREATE TABLE `sports_teams` (
  `TeamID` int(11) NOT NULL,
  `TeamGUID` char(36) NOT NULL,
  `TeamIDLive` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `TeamName` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
  `TeamNameShort` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL,
  `TeamFlag` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_team_players`
--

CREATE TABLE `sports_team_players` (
  `id` int(11) NOT NULL,
  `PlayerID` int(11) NOT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `MatchID` int(11) NOT NULL,
  `TeamID` int(11) NOT NULL,
  `PlayerRole` enum('AllRounder','Batsman','Bowler','WicketKeeper','Other') NOT NULL,
  `PlayerSalary` float(8,2) NOT NULL DEFAULT 0.00,
  `IsPlaying` enum('Yes','No') NOT NULL DEFAULT 'No',
  `TotalPoints` float(8,2) DEFAULT 0.00,
  `PointsData` text DEFAULT NULL,
  `PointsDataSecondInng` text DEFAULT NULL,
  `IsAdminUpdate` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsDfsPlay` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsActive` enum('Yes','No') NOT NULL DEFAULT 'Yes',
  `IsDublicate` enum('Yes','No') DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_team_players_completed`
--

CREATE TABLE `sports_team_players_completed` (
  `id` int(11) NOT NULL,
  `PlayerID` int(11) NOT NULL,
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `MatchID` int(11) NOT NULL,
  `TeamID` int(11) NOT NULL,
  `PlayerRole` enum('AllRounder','Batsman','Bowler','WicketKeeper','Other') NOT NULL,
  `PlayerSalary` float(8,2) NOT NULL DEFAULT 0.00,
  `IsPlaying` enum('Yes','No') NOT NULL DEFAULT 'No',
  `TotalPoints` float(8,2) DEFAULT 0.00,
  `PointsData` text DEFAULT NULL,
  `PointsDataSecondInng` text DEFAULT NULL,
  `IsAdminUpdate` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsDfsPlay` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsActive` enum('Yes','No') NOT NULL DEFAULT 'Yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_users_teams`
--

CREATE TABLE `sports_users_teams` (
  `UserTeamID` int(11) NOT NULL,
  `UserTeamGUID` varchar(36) NOT NULL,
  `UserID` int(11) NOT NULL,
  `UserTeamName` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `UserTeamType` enum('Normal','Auction','Draft') NOT NULL DEFAULT 'Normal',
  `MatchID` int(11) DEFAULT NULL,
  `SeriesID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `ContestID` int(11) DEFAULT NULL,
  `IsPreTeam` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsAssistant` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsVirtual` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionTopPlayerSubmitted` enum('Yes','No') NOT NULL DEFAULT 'No',
  `MatchInning` enum('First','Second','Third','Fourth') DEFAULT NULL,
  `IsUserBackupDone` enum('Yes','No') NOT NULL DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_users_teams_completed`
--

CREATE TABLE `sports_users_teams_completed` (
  `UserTeamID` int(11) NOT NULL,
  `UserTeamGUID` varchar(36) NOT NULL,
  `UserID` int(11) NOT NULL,
  `UserTeamName` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `UserTeamType` enum('Normal','Auction','Draft') NOT NULL DEFAULT 'Normal',
  `MatchID` int(11) DEFAULT NULL,
  `SeriesID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `ContestID` int(11) DEFAULT NULL,
  `IsPreTeam` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsAssistant` enum('Yes','No') NOT NULL DEFAULT 'No',
  `IsVirtual` enum('Yes','No') NOT NULL DEFAULT 'No',
  `AuctionTopPlayerSubmitted` enum('Yes','No') NOT NULL DEFAULT 'No',
  `MatchInning` enum('First','Second','Third','Fourth') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_users_team_players`
--

CREATE TABLE `sports_users_team_players` (
  `UserTeamID` int(11) NOT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `PlayerID` int(11) NOT NULL,
  `PlayerPosition` enum('Player','Captain','ViceCaptain') NOT NULL,
  `Points` float(8,2) NOT NULL,
  `BidCredit` decimal(10,0) DEFAULT NULL,
  `SeriesID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `DateTime` datetime DEFAULT NULL,
  `AuctionPlayingPlayer` enum('No','Yes') NOT NULL DEFAULT 'No',
  `AuctionDraftAssistantPriority` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sports_users_team_players_completed`
--

CREATE TABLE `sports_users_team_players_completed` (
  `UserTeamID` int(11) NOT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `PlayerID` int(11) NOT NULL,
  `PlayerPosition` enum('Player','Captain','ViceCaptain') NOT NULL,
  `Points` float(8,2) NOT NULL,
  `BidCredit` decimal(10,0) DEFAULT NULL,
  `SeriesID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `DateTime` datetime DEFAULT NULL,
  `AuctionPlayingPlayer` enum('No','Yes') NOT NULL DEFAULT 'No',
  `AuctionDraftAssistantPriority` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_action`
--

CREATE TABLE `tbl_action` (
  `EntityID` int(11) NOT NULL,
  `ToEntityID` int(11) NOT NULL,
  `Action` enum('Liked','Flagged','Saved','Blocked') NOT NULL,
  `Caption` varchar(255) DEFAULT NULL,
  `EntryDate` datetime NOT NULL,
  `StatusID` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_auction_player_bid`
--

CREATE TABLE `tbl_auction_player_bid` (
  `SeriesID` int(11) NOT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `ContestID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `PlayerID` int(11) NOT NULL,
  `BidCredit` bigint(20) NOT NULL,
  `IsSold` enum('Yes','No') NOT NULL DEFAULT 'No',
  `DateTime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_auction_player_bid_status`
--

CREATE TABLE `tbl_auction_player_bid_status` (
  `ID` int(11) NOT NULL,
  `SeriesID` int(11) NOT NULL,
  `MatchID` int(11) DEFAULT NULL,
  `RoundID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `ContestID` int(11) NOT NULL,
  `PlayerID` int(11) NOT NULL,
  `PlayerRole` enum('AllRounder','Batsman','Bowler','WicketKeeper','Other') NOT NULL DEFAULT 'Other',
  `BidCredit` decimal(10,0) NOT NULL,
  `PlayerStatus` enum('Upcoming','Live','Sold','Unsold') NOT NULL DEFAULT 'Upcoming',
  `DateTime` datetime DEFAULT NULL,
  `CreateDateTime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_broadcast_scheduling`
--

CREATE TABLE `tbl_broadcast_scheduling` (
  `ID` int(11) NOT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `Text` text DEFAULT NULL,
  `Date` datetime DEFAULT NULL,
  `IsSend` enum('No','Yes') DEFAULT 'No',
  `Status` int(1) DEFAULT NULL,
  `Push` int(1) DEFAULT 0,
  `Normal` int(1) DEFAULT 0,
  `Redirection` varchar(10) DEFAULT NULL,
  `EnteryDate` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_broadcast_scheduling`
--

INSERT INTO `tbl_broadcast_scheduling` (`ID`, `Title`, `Text`, `Date`, `IsSend`, `Status`, `Push`, `Normal`, `Redirection`, `EnteryDate`) VALUES
(1, 'Sam', 'teststts sandknask asjdkasdkhasuk asdukaskdhukahskdhkha ashdkusahkd aksuhdukasdukhasukhduk ukashdukahskudhukahs aksukdsahudhukaskdhukasd akakshdhakskdaskduaksd hsuahduahsudhuash uuiiii', '2020-04-06 04:00:00', 'No', NULL, 0, 0, NULL, '2020-04-06 10:03:47'),
(2, 'sdsdqweqwe12123123123', 'sdsdsdsd123123123', '2020-04-10 11:55:00', 'No', NULL, 0, 0, NULL, '2020-04-06 10:03:47'),
(3, 'sumit', 'testttttstsss', '2020-04-25 14:10:00', 'No', NULL, 0, 0, NULL, '2020-04-06 10:03:47'),
(4, 'sadasd', 'asdasdd', '2020-04-15 14:55:00', 'No', NULL, 0, 0, NULL, '2020-04-06 10:03:47'),
(5, 'Test', 'Test new', '2020-04-06 10:20:00', 'No', NULL, 0, 0, NULL, '2020-04-06 10:03:47'),
(6, 'Test', 'Play & Win', '2020-04-07 10:00:00', 'No', NULL, 0, 0, NULL, '2020-04-07 04:25:11'),
(7, 'tt', 'ttttttttt', '2020-05-20 15:55:00', 'No', NULL, 0, 0, NULL, '2020-05-20 10:08:49'),
(10, 'ss', 'sdsdsdsd', '2020-05-30 16:35:00', 'No', NULL, 1, 1, NULL, '2020-05-20 11:04:12'),
(11, 'test', 'hi', '2020-05-30 19:55:00', 'No', NULL, 1, 0, 'Dfs', '2020-05-22 13:21:54'),
(12, 'sdasd', 'sadasdasd', '2020-05-23 18:55:00', 'No', NULL, 1, 1, 'FT-Dfs', '2020-05-22 13:27:25');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_daily_invoice_report`
--

CREATE TABLE `tbl_daily_invoice_report` (
  `InvoiceID` int(11) NOT NULL,
  `InvoiceNumber` varchar(255) DEFAULT NULL,
  `BilledTo` varchar(255) DEFAULT NULL,
  `ContestGUID` varchar(255) DEFAULT NULL,
  `TaxableValue` float(8,3) NOT NULL,
  `SGST` float(8,3) NOT NULL,
  `CGST` float(8,3) NOT NULL,
  `InvoiceTotal` float(8,3) NOT NULL,
  `CreatedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_entity`
--

CREATE TABLE `tbl_entity` (
  `EntityID` int(11) NOT NULL,
  `EntityGUID` char(36) NOT NULL,
  `EntityTypeID` int(11) NOT NULL,
  `CreatedByUserID` int(11) DEFAULT NULL,
  `Rating` decimal(10,0) NOT NULL DEFAULT 0,
  `LikedCount` int(11) NOT NULL DEFAULT 0,
  `ViewCount` int(11) NOT NULL DEFAULT 0,
  `SharedCount` int(11) NOT NULL DEFAULT 0,
  `FlaggedCount` int(11) NOT NULL DEFAULT 0 COMMENT 'inappropriate content',
  `SavedCount` int(11) NOT NULL DEFAULT 0,
  `BlockedCount` int(11) NOT NULL DEFAULT 0,
  `EntryDate` datetime NOT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  `MenuOrder` int(11) DEFAULT NULL,
  `StatusID` int(11) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_entity`
--

INSERT INTO `tbl_entity` (`EntityID`, `EntityGUID`, `EntityTypeID`, `CreatedByUserID`, `Rating`, `LikedCount`, `ViewCount`, `SharedCount`, `FlaggedCount`, `SavedCount`, `BlockedCount`, `EntryDate`, `ModifiedDate`, `MenuOrder`, `StatusID`) VALUES
(2, '261020f1-85eb-e484-fff0-e5a1e825eca8', 1, NULL, '0', 0, 0, 0, 0, 0, 0, '2021-05-03 12:55:04', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_entity_categories`
--

CREATE TABLE `tbl_entity_categories` (
  `EntityID` int(11) NOT NULL,
  `CategoryID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_entity_type`
--

CREATE TABLE `tbl_entity_type` (
  `EntityTypeID` int(11) NOT NULL,
  `EntityTypeName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_entity_type`
--

INSERT INTO `tbl_entity_type` (`EntityTypeID`, `EntityTypeName`) VALUES
(1, 'User'),
(2, 'Media'),
(3, 'Category'),
(4, 'Group'),
(5, 'Event'),
(6, 'Post'),
(7, 'Series'),
(8, 'Matches'),
(9, 'Teams'),
(10, 'Players'),
(11, 'Contest'),
(12, 'User Teams'),
(13, 'Coupon'),
(14, 'Banner');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_entity_views`
--

CREATE TABLE `tbl_entity_views` (
  `UserID` int(11) NOT NULL,
  `EntityID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_lucky_wheel`
--

CREATE TABLE `tbl_lucky_wheel` (
  `PointsID` int(11) NOT NULL,
  `Points` varchar(255) DEFAULT NULL,
  `ColourCode` varchar(50) NOT NULL,
  `Pick` enum('Yes','No') DEFAULT 'Yes',
  `Image` varchar(255) DEFAULT '',
  `Status` enum('Yes','No') DEFAULT 'Yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_lucky_wheel_transaction`
--

CREATE TABLE `tbl_lucky_wheel_transaction` (
  `ID` int(11) NOT NULL,
  `LuckyWheelGUID` varchar(255) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `Value` varchar(50) DEFAULT NULL,
  `EntryDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_media`
--

CREATE TABLE `tbl_media` (
  `MediaID` int(11) NOT NULL,
  `MediaGUID` char(36) NOT NULL,
  `IsImage` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL COMMENT 'Uploaded by User',
  `SectionID` char(20) NOT NULL,
  `EntityID` int(11) DEFAULT NULL,
  `MediaRealName` varchar(255) DEFAULT NULL,
  `MediaName` varchar(100) DEFAULT NULL,
  `MediaSize` float DEFAULT NULL,
  `MediaExt` varchar(15) DEFAULT NULL,
  `MediaCaption` text DEFAULT NULL,
  `SortBy` int(11) NOT NULL DEFAULT 0,
  `IsActive` enum('Yes','No') DEFAULT 'Yes'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_media_sections`
--

CREATE TABLE `tbl_media_sections` (
  `SectionID` char(20) NOT NULL,
  `SectionFolderPath` varchar(255) NOT NULL,
  `SectionThumbSize` varchar(25) DEFAULT NULL,
  `SectionMaintainRatio` enum('Yes','No') DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_media_sections`
--

INSERT INTO `tbl_media_sections` (`SectionID`, `SectionFolderPath`, `SectionThumbSize`, `SectionMaintainRatio`) VALUES
('Aadhar', 'uploads/Aadhar/', '1100', 'No'),
('AadharBack', 'uploads/AadharBack/', '1100', 'No'),
('BankDetail', 'uploads/BankDetail/', '1100', 'No'),
('Banner', 'uploads/Banner/', '323230', 'No'),
('Category', 'uploads/category/', '220', 'No'),
('Coupon', 'uploads/Coupon/', '220', 'No'),
('File', 'uploads/file/', NULL, NULL),
('PAN', 'uploads/PAN/', '1100', 'No'),
('PlayerPic', 'uploads/PlayerPic/', '220', 'Yes'),
('Post', 'uploads/Post/', '220', 'No'),
('ProfileCoverPic', 'uploads/profile/cover/', '1100', 'Yes'),
('ProfilePic', 'uploads/profile/picture/', '220', 'Yes'),
('TeamFlag', 'uploads/TeamFlag/', '220', 'No');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_notifications`
--

CREATE TABLE `tbl_notifications` (
  `NotificationID` int(11) NOT NULL,
  `NotificationPatternID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `ToUserID` int(11) NOT NULL,
  `RefrenceID` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `NotificationText` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `NotificationMessage` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EntryDate` datetime NOT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  `StatusID` int(11) NOT NULL DEFAULT 1,
  `ReadCongratulations` enum('No','Yes') COLLATE utf8mb4_bin NOT NULL DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `tbl_notifications`
--

INSERT INTO `tbl_notifications` (`NotificationID`, `NotificationPatternID`, `UserID`, `ToUserID`, `RefrenceID`, `NotificationText`, `NotificationMessage`, `EntryDate`, `ModifiedDate`, `StatusID`, `ReadCongratulations`) VALUES
(427776, 1, 2, 2, NULL, 'Welcome to PickNPredict!', 'Hi Admin, Verify your Email.', '2021-05-03 12:55:07', NULL, 1, 'No');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_notification_pattern`
--

CREATE TABLE `tbl_notification_pattern` (
  `NotificationPatternID` int(11) NOT NULL,
  `NotificationPatternGUID` varchar(36) NOT NULL,
  `NotificationSampleText` varchar(255) DEFAULT NULL,
  `SendPushMessage` enum('Yes','No') NOT NULL DEFAULT 'No',
  `StatusID` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_notification_pattern`
--

INSERT INTO `tbl_notification_pattern` (`NotificationPatternID`, `NotificationPatternGUID`, `NotificationSampleText`, `SendPushMessage`, `StatusID`) VALUES
(1, 'welcome', 'Hi and welcome to SITE_NAME', 'No', 2),
(2, 'broadcast', 'broadcast message', 'Yes', 2),
(3, 'playingxi', 'PlayingXI message', 'No', 2),
(4, 'AddCash', 'Add Deposit message', 'Yes', 2),
(5, 'ReferralBonus', 'Referral Bonus message', 'Yes', 2),
(6, 'Withdrawal', 'Withdrawal message', 'Yes', 2),
(7, 'bonus', 'Signup bonus message', 'Yes', 2),
(8, 'refund', 'Contest Cancel Refund message', 'Yes', 2),
(9, 'verify', 'Verify Message', 'Yes', 2),
(10, 'winnings', 'Winning Bonus', 'Yes', 2),
(11, 'Gully', 'Gully Auction', 'Yes', 2),
(12, 'Auction', 'Auction Auction', 'Yes', 2);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_offers`
--

CREATE TABLE `tbl_offers` (
  `ID` int(11) NOT NULL,
  `OfferType` enum('Offer-1','Offer-2') NOT NULL,
  `OfferPercent` int(11) NOT NULL,
  `OfferName` varchar(100) NOT NULL,
  `MatchID` int(11) NOT NULL,
  `ContestIDs` text DEFAULT NULL,
  `OfferDateTime` datetime DEFAULT NULL,
  `NoOfTeams` int(11) NOT NULL,
  `UserSelectionType` enum('AllUsers','SelectedUsers','RandomUsers') NOT NULL,
  `NoOfRandomUsers` int(11) NOT NULL DEFAULT 0,
  `UserIDs` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_referral_codes`
--

CREATE TABLE `tbl_referral_codes` (
  `ReferralCodeID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `ReferralCode` varchar(11) NOT NULL,
  `StatusID` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_referral_codes`
--

INSERT INTO `tbl_referral_codes` (`ReferralCodeID`, `UserID`, `ReferralCode`, `StatusID`) VALUES
(77659, 2, 'blVa4A', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_subscription`
--

CREATE TABLE `tbl_subscription` (
  `ID` int(11) NOT NULL,
  `Title` varchar(100) NOT NULL,
  `Days` int(11) NOT NULL,
  `Price` double NOT NULL,
  `DiscountPrice` double DEFAULT NULL,
  `Text` text DEFAULT NULL,
  `EntryDate` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_subscription`
--

INSERT INTO `tbl_subscription` (`ID`, `Title`, `Days`, `Price`, `DiscountPrice`, `Text`, `EntryDate`) VALUES
(34, '1', 1, 149, 69, 'Extra Benefits By Star Membership', '2020-08-27 09:37:45'),
(37, '1', 30, 4470, 1788, 'Extra Benefits By Star Membership', '2020-08-27 09:39:05'),
(38, '1', 10, 1490, 679, 'Extra Benefits By Star Membership', '2020-08-27 09:49:13'),
(39, '1', 20, 2980, 1299, 'Extra Benefits By Star Membership', '2020-08-27 09:49:30');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_test_razorpay`
--

CREATE TABLE `tbl_test_razorpay` (
  `ID` int(11) NOT NULL,
  `OrderID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `PaymentType` enum('razorPay','phonePe') NOT NULL DEFAULT 'razorPay',
  `CaptureType` varchar(100) DEFAULT NULL,
  `Content` text DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_tokens`
--

CREATE TABLE `tbl_tokens` (
  `Type` enum('1','2','3') DEFAULT NULL COMMENT '1=Forgot Password, 2=Email Verification, 3=Phone No. Verification',
  `Token` varchar(100) NOT NULL,
  `UserID` int(11) NOT NULL,
  `EntryDate` datetime NOT NULL,
  `StatusID` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_tokens`
--

INSERT INTO `tbl_tokens` (`Type`, `Token`, `UserID`, `EntryDate`, `StatusID`) VALUES
('2', '495283', 2, '2021-05-03 12:55:04', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `UserID` int(11) NOT NULL,
  `UserGUID` char(36) NOT NULL,
  `UserTypeID` int(11) NOT NULL,
  `FirstName` varchar(100) DEFAULT NULL,
  `MiddleName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `About` mediumtext DEFAULT NULL,
  `About1` mediumtext DEFAULT NULL,
  `About2` mediumtext DEFAULT NULL,
  `ProfilePic` varchar(50) DEFAULT NULL,
  `ProfileCoverPic` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `EmailForChange` varchar(50) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL COMMENT 'Profile Slug (Team Code)',
  `IsUsernameUpdateded` enum('No','Yes') NOT NULL DEFAULT 'No',
  `Gender` enum('Male','Female','Other') DEFAULT NULL,
  `BirthDate` date DEFAULT NULL,
  `Age` int(11) DEFAULT NULL,
  `Height` varchar(100) DEFAULT NULL,
  `Weight` varchar(100) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Address1` varchar(255) DEFAULT NULL,
  `Postal` varchar(12) DEFAULT NULL,
  `CountryCode` char(2) DEFAULT NULL,
  `CityName` varchar(255) DEFAULT NULL,
  `StateName` varchar(255) DEFAULT NULL,
  `Latitude` float DEFAULT NULL,
  `Longitude` float DEFAULT NULL,
  `PhoneNumber` varchar(15) DEFAULT NULL,
  `PhoneNumberForChange` varchar(15) DEFAULT NULL,
  `Website` varchar(1000) DEFAULT NULL,
  `FacebookURL` varchar(255) DEFAULT NULL COMMENT 'FB Profile URL',
  `TwitterURL` varchar(255) DEFAULT NULL COMMENT 'Twitter Profile URL',
  `GoogleURL` varchar(255) DEFAULT NULL,
  `InstagramURL` varchar(255) DEFAULT NULL,
  `LinkedInURL` varchar(255) DEFAULT NULL,
  `WhatsApp` varchar(255) DEFAULT NULL,
  `ReferralCodeID` int(11) DEFAULT NULL,
  `ReferredByUserID` int(11) DEFAULT NULL,
  `WalletAmount` float(8,2) NOT NULL DEFAULT 0.00 COMMENT '(Deposit Amount)',
  `WinningAmount` float(8,2) NOT NULL COMMENT '(Contest Winning Amount)',
  `CashBonus` float(8,2) NOT NULL COMMENT '(Referral Bonus)',
  `WithdrawalHoldAmount` float(8,2) NOT NULL COMMENT '(Total Of Withdrawal Pending Request)',
  `isWithdrawal` enum('Yes','No') NOT NULL DEFAULT 'Yes',
  `PanStatus` int(2) DEFAULT 9,
  `BankStatus` int(2) NOT NULL DEFAULT 9,
  `AadharStatus` int(2) NOT NULL DEFAULT 9,
  `IsPrivacyNameDisplay` enum('Yes','No') NOT NULL DEFAULT 'No',
  `LoginType` enum('Web','Andorid','Ios') DEFAULT NULL,
  `BonusExpireTime` datetime DEFAULT NULL,
  `SubscriptionStart` datetime DEFAULT NULL,
  `SubscriptionEnd` datetime DEFAULT NULL,
  `BonusExpireStatus` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`UserID`, `UserGUID`, `UserTypeID`, `FirstName`, `MiddleName`, `LastName`, `About`, `About1`, `About2`, `ProfilePic`, `ProfileCoverPic`, `Email`, `EmailForChange`, `Username`, `IsUsernameUpdateded`, `Gender`, `BirthDate`, `Age`, `Height`, `Weight`, `Address`, `Address1`, `Postal`, `CountryCode`, `CityName`, `StateName`, `Latitude`, `Longitude`, `PhoneNumber`, `PhoneNumberForChange`, `Website`, `FacebookURL`, `TwitterURL`, `GoogleURL`, `InstagramURL`, `LinkedInURL`, `WhatsApp`, `ReferralCodeID`, `ReferredByUserID`, `WalletAmount`, `WinningAmount`, `CashBonus`, `WithdrawalHoldAmount`, `isWithdrawal`, `PanStatus`, `BankStatus`, `AadharStatus`, `IsPrivacyNameDisplay`, `LoginType`, `BonusExpireTime`, `SubscriptionStart`, `SubscriptionEnd`, `BonusExpireStatus`) VALUES
(2, '261020f1-85eb-e484-fff0-e5a1e825eca8', 1, 'Admin', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'admin@mailinator.com', 'jurhar', 'No', 'Male', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0.00, 0.00, 0.00, 0.00, 'Yes', 9, 9, 9, 'No', 'Web', NULL, NULL, NULL, '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users_login`
--

CREATE TABLE `tbl_users_login` (
  `UserID` int(11) NOT NULL,
  `Password` char(32) DEFAULT NULL,
  `SourceID` int(11) DEFAULT NULL,
  `EntryDate` datetime NOT NULL,
  `LastLoginDate` datetime DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  `IP` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_users_login`
--

INSERT INTO `tbl_users_login` (`UserID`, `Password`, `SourceID`, `EntryDate`, `LastLoginDate`, `ModifiedDate`, `IP`) VALUES
(2, 'e10adc3949ba59abbe56e057f20f883e', 1, '2021-05-03 12:55:04', '2021-05-03 12:55:07', NULL, '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users_logs`
--

CREATE TABLE `tbl_users_logs` (
  `LogID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `PhoneNumber` varchar(150) DEFAULT NULL,
  `Email` varchar(150) DEFAULT NULL,
  `Counter` smallint(6) NOT NULL DEFAULT 0,
  `IP` varchar(150) DEFAULT NULL,
  `LogType` enum('Login','Signup','Invite','Verify','OTP') DEFAULT NULL,
  `Status` enum('Open','Close') NOT NULL DEFAULT 'Open',
  `EntryDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_users_logs`
--

INSERT INTO `tbl_users_logs` (`LogID`, `UserID`, `PhoneNumber`, `Email`, `Counter`, `IP`, `LogType`, `Status`, `EntryDate`) VALUES
(16762, NULL, NULL, NULL, 2, '::1', 'Signup', 'Open', '2021-05-03 12:54:05');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users_session`
--

CREATE TABLE `tbl_users_session` (
  `UserID` int(11) NOT NULL,
  `SessionKey` char(36) NOT NULL,
  `IPAddress` varchar(15) DEFAULT NULL,
  `SourceID` int(11) DEFAULT NULL,
  `DeviceTypeID` int(11) DEFAULT NULL,
  `DeviceGUID` varchar(255) DEFAULT NULL,
  `DeviceToken` varchar(255) DEFAULT NULL,
  `EntryDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `tbl_users_session`
--

INSERT INTO `tbl_users_session` (`UserID`, `SessionKey`, `IPAddress`, `SourceID`, `DeviceTypeID`, `DeviceGUID`, `DeviceToken`, `EntryDate`) VALUES
(2, 'c9b47164-856f-4489-337a-b882f35948a7', NULL, 1, 1, NULL, NULL, '2021-05-03 12:55:07');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users_settings`
--

CREATE TABLE `tbl_users_settings` (
  `UserID` int(11) DEFAULT NULL,
  `PushNotification` enum('Yes','No') NOT NULL DEFAULT 'Yes',
  `PrivacyPhone` enum('Public','Private') NOT NULL DEFAULT 'Public',
  `PrivacyLocation` enum('Public','Private') NOT NULL DEFAULT 'Public'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_users_settings`
--

INSERT INTO `tbl_users_settings` (`UserID`, `PushNotification`, `PrivacyPhone`, `PrivacyLocation`) VALUES
(2, 'Yes', 'Public', 'Public');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users_type`
--

CREATE TABLE `tbl_users_type` (
  `UserTypeID` int(11) NOT NULL,
  `UserTypeGUID` varchar(36) DEFAULT NULL,
  `UserTypeName` varchar(100) NOT NULL,
  `IsAdmin` enum('Yes','No') NOT NULL DEFAULT 'Yes'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_users_type`
--

INSERT INTO `tbl_users_type` (`UserTypeID`, `UserTypeGUID`, `UserTypeName`, `IsAdmin`) VALUES
(1, NULL, 'Administrator', 'Yes'),
(2, '94580cfc-2274-dc87-1o01-e268aaab194c', 'User', 'No'),
(3, '94580cfc-7894-dc87-7b01-e268aaab194c', 'User2', 'No'),
(4, '4be4282e-181a-d4ba-bc58-13c336f0d345', 'Staff', 'Yes'),
(5, 'fb3fd4f1-4004-40ef-75d9-2e3c2b594547', 'Accounts ', 'Yes');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users_wallet`
--

CREATE TABLE `tbl_users_wallet` (
  `WalletID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `SubscriptionID` int(11) DEFAULT NULL,
  `Amount` float(8,2) NOT NULL COMMENT 'Total Amount (WalletAmount+WinningAmount+CashBonus)',
  `OpeningWalletAmount` float(8,2) NOT NULL,
  `OpeningWinningAmount` float(8,2) NOT NULL,
  `OpeningCashBonus` float(8,2) NOT NULL,
  `WalletAmount` float(8,2) NOT NULL,
  `WinningAmount` float(8,2) NOT NULL,
  `CashBonus` float(8,2) NOT NULL,
  `ClosingWalletAmount` float(8,2) NOT NULL,
  `ClosingWinningAmount` float(8,2) NOT NULL,
  `ClosingCashBonus` float(8,2) NOT NULL,
  `Currency` enum('INR','USD') NOT NULL DEFAULT 'INR',
  `PaymentGateway` enum('PayUmoney','Paytm','Razorpay','CashFree','PhonePe') DEFAULT NULL,
  `TransactionType` enum('Cr','Dr') NOT NULL,
  `TransactionID` varchar(255) DEFAULT NULL,
  `Narration` enum('Deposit Money','Join Contest','Cancel Contest','Signup Bonus','Admin Cash Bonus','Join Contest Winning','First Deposit Bonus','Verification Bonus','Referral Bonus','Coupon Discount','Withdrawal Request','Withdrawal Reject','Wrong Winning Distribution','Admin Deposit Money','Cash Bonus Expire','Coupon Discount Bonus','Referral Deposit','Referral Winning','Signup Direct Deposit','Refund Deposit','Join Contest Winning Bonus','Subscription','Deposit Money Star Member') NOT NULL,
  `AmountType` enum('Direct','Referral') NOT NULL DEFAULT 'Direct',
  `EntityID` int(11) DEFAULT NULL,
  `UserTeamID` int(11) DEFAULT NULL,
  `PaymentGatewayResponse` text DEFAULT NULL,
  `CouponDetails` text DEFAULT NULL COMMENT '(Applied Coupon Details)',
  `CouponCode` varchar(8) DEFAULT NULL,
  `ReferralGetAmountUserID` int(11) DEFAULT NULL,
  `EntryDate` datetime NOT NULL,
  `SportsType` enum('Cricket','Football','Stock') NOT NULL DEFAULT 'Cricket',
  `ModifiedDate` datetime DEFAULT NULL,
  `StatusID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users_withdrawal`
--

CREATE TABLE `tbl_users_withdrawal` (
  `WithdrawalID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `WalletID` varchar(255) DEFAULT NULL,
  `PaytmPhoneNumber` varchar(100) DEFAULT NULL,
  `OTP` varchar(10) DEFAULT NULL,
  `IsOTPVerified` enum('No','Yes') DEFAULT NULL,
  `Amount` float(8,2) NOT NULL,
  `PaymentGateway` enum('Paytm','Bank') NOT NULL,
  `PaymentGatewayResponse` text DEFAULT NULL,
  `EntryDate` datetime NOT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  `Comments` varchar(250) DEFAULT NULL,
  `StatusID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_control`
--
ALTER TABLE `admin_control`
  ADD PRIMARY KEY (`ControlID`),
  ADD KEY `moduleID` (`ModuleID`),
  ADD KEY `parentControlID` (`ParentControlID`);

--
-- Indexes for table `admin_modules`
--
ALTER TABLE `admin_modules`
  ADD PRIMARY KEY (`ModuleID`);

--
-- Indexes for table `admin_user_type_permission`
--
ALTER TABLE `admin_user_type_permission`
  ADD UNIQUE KEY `groupID_2` (`UserTypeID`,`ModuleID`),
  ADD KEY `groupID` (`UserTypeID`),
  ADD KEY `moduleID` (`ModuleID`);

--
-- Indexes for table `ecom_coupon`
--
ALTER TABLE `ecom_coupon`
  ADD PRIMARY KEY (`CouponID`);

--
-- Indexes for table `football_sports_contest`
--
ALTER TABLE `football_sports_contest`
  ADD PRIMARY KEY (`ContestID`),
  ADD UNIQUE KEY `UserInvitationCode` (`UserInvitationCode`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `ContestGUID` (`ContestGUID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ContestType` (`ContestType`),
  ADD KEY `Privacy` (`Privacy`),
  ADD KEY `IsPaid` (`IsPaid`),
  ADD KEY `sports_contest_ibfk_8` (`AuctionStatusID`);

--
-- Indexes for table `football_sports_contest_join`
--
ALTER TABLE `football_sports_contest_join`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ContestID` (`ContestID`),
  ADD KEY `UserTeamID` (`UserTeamID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `SeriesID` (`SeriesID`);

--
-- Indexes for table `football_sports_matches`
--
ALTER TABLE `football_sports_matches`
  ADD PRIMARY KEY (`MatchID`),
  ADD KEY `MatchIDLive` (`MatchIDLive`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `TeamIDLocal` (`TeamIDLocal`),
  ADD KEY `TeamIDVisitor` (`TeamIDVisitor`),
  ADD KEY `MatchTypeID` (`MatchTypeID`),
  ADD KEY `MatchGUID` (`MatchGUID`);

--
-- Indexes for table `football_sports_players`
--
ALTER TABLE `football_sports_players`
  ADD PRIMARY KEY (`PlayerID`),
  ADD UNIQUE KEY `PlayerIDLive` (`PlayerIDLive`) USING BTREE,
  ADD KEY `PlayerGUID` (`PlayerGUID`);

--
-- Indexes for table `football_sports_pre_contest`
--
ALTER TABLE `football_sports_pre_contest`
  ADD PRIMARY KEY (`PreContestID`);

--
-- Indexes for table `football_sports_series`
--
ALTER TABLE `football_sports_series`
  ADD PRIMARY KEY (`SeriesID`),
  ADD UNIQUE KEY `SeriesIDLive_2` (`SeriesIDLive`),
  ADD KEY `SeriesIDLive` (`SeriesIDLive`),
  ADD KEY `SeriesGUID` (`SeriesGUID`),
  ADD KEY `sports_series_ibfk_2` (`AuctionDraftStatusID`);

--
-- Indexes for table `football_sports_series_rounds`
--
ALTER TABLE `football_sports_series_rounds`
  ADD PRIMARY KEY (`RoundID`),
  ADD UNIQUE KEY `SeriesIDLive_2` (`RoundIDLive`),
  ADD KEY `SeriesIDLive` (`RoundIDLive`),
  ADD KEY `sports_series_ibfk_2` (`AuctionDraftStatusID`),
  ADD KEY `SeriesID` (`SeriesID`);

--
-- Indexes for table `football_sports_setting_points`
--
ALTER TABLE `football_sports_setting_points`
  ADD UNIQUE KEY `PointsTypeGUID` (`PointsTypeGUID`),
  ADD KEY `StatusID` (`StatusID`),
  ADD KEY `PointsInningType` (`PointsInningType`);

--
-- Indexes for table `football_sports_set_match_types`
--
ALTER TABLE `football_sports_set_match_types`
  ADD PRIMARY KEY (`MatchTypeID`);

--
-- Indexes for table `football_sports_teams`
--
ALTER TABLE `football_sports_teams`
  ADD PRIMARY KEY (`TeamID`),
  ADD UNIQUE KEY `TeamIDLive` (`TeamIDLive`);

--
-- Indexes for table `football_sports_team_players`
--
ALTER TABLE `football_sports_team_players`
  ADD PRIMARY KEY (`id`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `TeamID` (`TeamID`),
  ADD KEY `PlayerID` (`PlayerID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `IsPlaying` (`IsPlaying`),
  ADD KEY `PlayerRole` (`PlayerRole`);

--
-- Indexes for table `football_sports_users_teams`
--
ALTER TABLE `football_sports_users_teams`
  ADD PRIMARY KEY (`UserTeamID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `UserTeamGUID` (`UserTeamGUID`),
  ADD KEY `UserTeamID` (`UserTeamID`,`MatchID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `ContestID` (`ContestID`),
  ADD KEY `RoundID` (`RoundID`);

--
-- Indexes for table `football_sports_users_team_players`
--
ALTER TABLE `football_sports_users_team_players`
  ADD UNIQUE KEY `UserTeamID_2` (`UserTeamID`,`MatchID`,`PlayerID`),
  ADD KEY `UserTeamID` (`UserTeamID`),
  ADD KEY `PlayerID` (`PlayerID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `PlayerPosition` (`PlayerPosition`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `RoundID` (`RoundID`);

--
-- Indexes for table `logs_paytm`
--
ALTER TABLE `logs_paytm`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `log_api`
--
ALTER TABLE `log_api`
  ADD PRIMARY KEY (`LogID`);

--
-- Indexes for table `log_cron`
--
ALTER TABLE `log_cron`
  ADD PRIMARY KEY (`CronID`);

--
-- Indexes for table `log_pushdata`
--
ALTER TABLE `log_pushdata`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `set_categories`
--
ALTER TABLE `set_categories`
  ADD PRIMARY KEY (`CategoryID`),
  ADD KEY `CategoryTypeID` (`CategoryTypeID`);

--
-- Indexes for table `set_categories_type`
--
ALTER TABLE `set_categories_type`
  ADD PRIMARY KEY (`CategoryTypeID`),
  ADD KEY `StatusID` (`StatusID`);

--
-- Indexes for table `set_device_type`
--
ALTER TABLE `set_device_type`
  ADD PRIMARY KEY (`DeviceTypeID`);

--
-- Indexes for table `set_pages`
--
ALTER TABLE `set_pages`
  ADD PRIMARY KEY (`PageID`);

--
-- Indexes for table `set_site_config`
--
ALTER TABLE `set_site_config`
  ADD UNIQUE KEY `ConfigurationTypeGUID` (`ConfigTypeGUID`),
  ADD KEY `ConfigTypeStatus` (`StatusID`);

--
-- Indexes for table `set_source`
--
ALTER TABLE `set_source`
  ADD PRIMARY KEY (`SourceID`);

--
-- Indexes for table `set_status`
--
ALTER TABLE `set_status`
  ADD PRIMARY KEY (`StatusID`);

--
-- Indexes for table `set_virtual_names`
--
ALTER TABLE `set_virtual_names`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `social_post`
--
ALTER TABLE `social_post`
  ADD UNIQUE KEY `PostID` (`PostID`),
  ADD KEY `UserID` (`EntityID`),
  ADD KEY `EntityID` (`ToEntityID`);
ALTER TABLE `social_post` ADD FULLTEXT KEY `PostContent` (`PostContent`);
ALTER TABLE `social_post` ADD FULLTEXT KEY `Caption` (`PostCaption`);

--
-- Indexes for table `social_subscribers`
--
ALTER TABLE `social_subscribers`
  ADD KEY `StatusID` (`StatusID`),
  ADD KEY `EntityID` (`ToEntityID`),
  ADD KEY `social_subscribers_ibfk_5` (`UserID`);

--
-- Indexes for table `sports_announcement`
--
ALTER TABLE `sports_announcement`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `sports_auction_draft_player_point`
--
ALTER TABLE `sports_auction_draft_player_point`
  ADD KEY `sports_auction_draft_player_point_ibfk1` (`SeriesID`),
  ADD KEY `sports_auction_draft_player_point_ibfk2` (`ContestID`),
  ADD KEY `sports_auction_draft_player_point_ibfk3` (`PlayerID`);

--
-- Indexes for table `sports_auction_draft_series_players`
--
ALTER TABLE `sports_auction_draft_series_players`
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `TeamID` (`TeamID`),
  ADD KEY `PlayerID` (`PlayerID`),
  ADD KEY `PlayerRole` (`PlayerRole`);

--
-- Indexes for table `sports_contest`
--
ALTER TABLE `sports_contest`
  ADD PRIMARY KEY (`ContestID`),
  ADD UNIQUE KEY `UserInvitationCode` (`UserInvitationCode`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `ContestGUID` (`ContestGUID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ContestType` (`ContestType`),
  ADD KEY `Privacy` (`Privacy`),
  ADD KEY `IsPaid` (`IsPaid`),
  ADD KEY `sports_contest_ibfk_8` (`AuctionStatusID`);

--
-- Indexes for table `sports_contest_completed`
--
ALTER TABLE `sports_contest_completed`
  ADD PRIMARY KEY (`ContestID`),
  ADD UNIQUE KEY `UserInvitationCode` (`UserInvitationCode`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `ContestGUID` (`ContestGUID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ContestType` (`ContestType`),
  ADD KEY `Privacy` (`Privacy`),
  ADD KEY `IsPaid` (`IsPaid`),
  ADD KEY `sports_contest_ibfk_8` (`AuctionStatusID`);

--
-- Indexes for table `sports_contest_join`
--
ALTER TABLE `sports_contest_join`
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ContestID` (`ContestID`),
  ADD KEY `UserTeamID` (`UserTeamID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `SeriesID` (`SeriesID`);

--
-- Indexes for table `sports_contest_join_completed`
--
ALTER TABLE `sports_contest_join_completed`
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ContestID` (`ContestID`),
  ADD KEY `UserTeamID` (`UserTeamID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `SeriesID` (`SeriesID`);

--
-- Indexes for table `sports_matches`
--
ALTER TABLE `sports_matches`
  ADD PRIMARY KEY (`MatchID`),
  ADD KEY `MatchIDLive` (`MatchIDLive`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `TeamIDLocal` (`TeamIDLocal`),
  ADD KEY `TeamIDVisitor` (`TeamIDVisitor`),
  ADD KEY `MatchTypeID` (`MatchTypeID`),
  ADD KEY `MatchGUID` (`MatchGUID`);

--
-- Indexes for table `sports_players`
--
ALTER TABLE `sports_players`
  ADD PRIMARY KEY (`PlayerID`),
  ADD UNIQUE KEY `PlayerIDLive` (`PlayerIDLive`) USING BTREE,
  ADD KEY `PlayerGUID` (`PlayerGUID`);

--
-- Indexes for table `sports_pre_contest`
--
ALTER TABLE `sports_pre_contest`
  ADD PRIMARY KEY (`PreContestID`);

--
-- Indexes for table `sports_series`
--
ALTER TABLE `sports_series`
  ADD PRIMARY KEY (`SeriesID`),
  ADD UNIQUE KEY `SeriesIDLive_2` (`SeriesIDLive`),
  ADD KEY `SeriesIDLive` (`SeriesIDLive`),
  ADD KEY `SeriesGUID` (`SeriesGUID`),
  ADD KEY `sports_series_ibfk_2` (`AuctionDraftStatusID`);

--
-- Indexes for table `sports_series_rounds`
--
ALTER TABLE `sports_series_rounds`
  ADD PRIMARY KEY (`RoundID`),
  ADD UNIQUE KEY `SeriesIDLive_2` (`RoundIDLive`),
  ADD KEY `SeriesIDLive` (`RoundIDLive`),
  ADD KEY `sports_series_ibfk_2` (`AuctionDraftStatusID`),
  ADD KEY `SeriesID` (`SeriesID`);

--
-- Indexes for table `sports_setting_points`
--
ALTER TABLE `sports_setting_points`
  ADD UNIQUE KEY `PointsTypeGUID` (`PointsTypeGUID`),
  ADD KEY `PointsT20` (`PointsT20`),
  ADD KEY `PointsODI` (`PointsODI`),
  ADD KEY `PointsTEST` (`PointsTEST`),
  ADD KEY `StatusID` (`StatusID`),
  ADD KEY `PointsInningType` (`PointsInningType`);

--
-- Indexes for table `sports_set_match_types`
--
ALTER TABLE `sports_set_match_types`
  ADD PRIMARY KEY (`MatchTypeID`);

--
-- Indexes for table `sports_teams`
--
ALTER TABLE `sports_teams`
  ADD PRIMARY KEY (`TeamID`),
  ADD UNIQUE KEY `TeamIDLive` (`TeamIDLive`);

--
-- Indexes for table `sports_team_players`
--
ALTER TABLE `sports_team_players`
  ADD PRIMARY KEY (`id`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `TeamID` (`TeamID`),
  ADD KEY `PlayerID` (`PlayerID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `IsPlaying` (`IsPlaying`),
  ADD KEY `PlayerRole` (`PlayerRole`);

--
-- Indexes for table `sports_team_players_completed`
--
ALTER TABLE `sports_team_players_completed`
  ADD PRIMARY KEY (`id`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `TeamID` (`TeamID`),
  ADD KEY `PlayerID` (`PlayerID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `IsPlaying` (`IsPlaying`),
  ADD KEY `PlayerRole` (`PlayerRole`);

--
-- Indexes for table `sports_users_teams`
--
ALTER TABLE `sports_users_teams`
  ADD PRIMARY KEY (`UserTeamID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `UserTeamGUID` (`UserTeamGUID`),
  ADD KEY `UserTeamID` (`UserTeamID`,`MatchID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `ContestID` (`ContestID`),
  ADD KEY `RoundID` (`RoundID`);

--
-- Indexes for table `sports_users_teams_completed`
--
ALTER TABLE `sports_users_teams_completed`
  ADD PRIMARY KEY (`UserTeamID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `UserTeamGUID` (`UserTeamGUID`),
  ADD KEY `UserTeamID` (`UserTeamID`,`MatchID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `ContestID` (`ContestID`),
  ADD KEY `RoundID` (`RoundID`);

--
-- Indexes for table `sports_users_team_players`
--
ALTER TABLE `sports_users_team_players`
  ADD UNIQUE KEY `UserTeamID_2` (`UserTeamID`,`MatchID`,`PlayerID`),
  ADD KEY `UserTeamID` (`UserTeamID`),
  ADD KEY `PlayerID` (`PlayerID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `PlayerPosition` (`PlayerPosition`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `RoundID` (`RoundID`);

--
-- Indexes for table `sports_users_team_players_completed`
--
ALTER TABLE `sports_users_team_players_completed`
  ADD UNIQUE KEY `UserTeamID_2` (`UserTeamID`,`MatchID`,`PlayerID`),
  ADD KEY `UserTeamID` (`UserTeamID`),
  ADD KEY `PlayerID` (`PlayerID`),
  ADD KEY `MatchID` (`MatchID`),
  ADD KEY `PlayerPosition` (`PlayerPosition`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `RoundID` (`RoundID`);

--
-- Indexes for table `tbl_action`
--
ALTER TABLE `tbl_action`
  ADD KEY `EntityID` (`EntityID`),
  ADD KEY `ToEntityID` (`ToEntityID`),
  ADD KEY `StatusID` (`StatusID`);

--
-- Indexes for table `tbl_auction_player_bid`
--
ALTER TABLE `tbl_auction_player_bid`
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `ContestID` (`ContestID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `PlayerID` (`PlayerID`);

--
-- Indexes for table `tbl_auction_player_bid_status`
--
ALTER TABLE `tbl_auction_player_bid_status`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `SeriesID` (`SeriesID`),
  ADD KEY `ContestID` (`ContestID`),
  ADD KEY `PlayerID` (`PlayerID`);

--
-- Indexes for table `tbl_broadcast_scheduling`
--
ALTER TABLE `tbl_broadcast_scheduling`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_daily_invoice_report`
--
ALTER TABLE `tbl_daily_invoice_report`
  ADD PRIMARY KEY (`InvoiceID`);

--
-- Indexes for table `tbl_entity`
--
ALTER TABLE `tbl_entity`
  ADD PRIMARY KEY (`EntityID`),
  ADD KEY `ModuleID` (`EntityTypeID`),
  ADD KEY `StatusID` (`StatusID`),
  ADD KEY `CreatedByUserID` (`CreatedByUserID`),
  ADD KEY `EntityGUID` (`EntityGUID`);

--
-- Indexes for table `tbl_entity_categories`
--
ALTER TABLE `tbl_entity_categories`
  ADD KEY `ProductID` (`EntityID`),
  ADD KEY `CategoryID` (`CategoryID`);

--
-- Indexes for table `tbl_entity_type`
--
ALTER TABLE `tbl_entity_type`
  ADD PRIMARY KEY (`EntityTypeID`);

--
-- Indexes for table `tbl_entity_views`
--
ALTER TABLE `tbl_entity_views`
  ADD UNIQUE KEY `UserID` (`UserID`,`EntityID`),
  ADD KEY `EntityID` (`EntityID`);

--
-- Indexes for table `tbl_lucky_wheel`
--
ALTER TABLE `tbl_lucky_wheel`
  ADD PRIMARY KEY (`PointsID`);

--
-- Indexes for table `tbl_lucky_wheel_transaction`
--
ALTER TABLE `tbl_lucky_wheel_transaction`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_media`
--
ALTER TABLE `tbl_media`
  ADD KEY `MediaID` (`MediaID`),
  ADD KEY `SectionID` (`SectionID`),
  ADD KEY `EntityID` (`EntityID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `tbl_media_sections`
--
ALTER TABLE `tbl_media_sections`
  ADD PRIMARY KEY (`SectionID`),
  ADD KEY `SectionName` (`SectionID`);

--
-- Indexes for table `tbl_notifications`
--
ALTER TABLE `tbl_notifications`
  ADD PRIMARY KEY (`NotificationID`),
  ADD KEY `NotificationTypeID` (`NotificationPatternID`),
  ADD KEY `StatusID` (`StatusID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ToUserID` (`ToUserID`);

--
-- Indexes for table `tbl_notification_pattern`
--
ALTER TABLE `tbl_notification_pattern`
  ADD PRIMARY KEY (`NotificationPatternID`),
  ADD UNIQUE KEY `NotificationTypeGUID` (`NotificationPatternGUID`);

--
-- Indexes for table `tbl_offers`
--
ALTER TABLE `tbl_offers`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `MatchID` (`MatchID`);

--
-- Indexes for table `tbl_referral_codes`
--
ALTER TABLE `tbl_referral_codes`
  ADD PRIMARY KEY (`ReferralCodeID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `ReferralCode` (`ReferralCode`,`StatusID`),
  ADD KEY `StatusID` (`StatusID`);

--
-- Indexes for table `tbl_subscription`
--
ALTER TABLE `tbl_subscription`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_test_razorpay`
--
ALTER TABLE `tbl_test_razorpay`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_tokens`
--
ALTER TABLE `tbl_tokens`
  ADD KEY `userID` (`UserID`),
  ADD KEY `StatusID` (`StatusID`),
  ADD KEY `Type` (`Type`,`Token`,`StatusID`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD UNIQUE KEY `UserID` (`UserID`),
  ADD UNIQUE KEY `Email` (`Email`),
  ADD UNIQUE KEY `Username` (`Username`),
  ADD UNIQUE KEY `PhoneNumber` (`PhoneNumber`),
  ADD KEY `UserTypeID` (`UserTypeID`),
  ADD KEY `ReferralCodeID` (`ReferralCodeID`),
  ADD KEY `ReferredByUserID` (`ReferredByUserID`),
  ADD KEY `UserGUID` (`UserGUID`);

--
-- Indexes for table `tbl_users_login`
--
ALTER TABLE `tbl_users_login`
  ADD UNIQUE KEY `UserID` (`UserID`,`Password`,`SourceID`),
  ADD KEY `fk_UserLogins_2` (`SourceID`),
  ADD KEY `Password` (`Password`,`SourceID`);

--
-- Indexes for table `tbl_users_logs`
--
ALTER TABLE `tbl_users_logs`
  ADD PRIMARY KEY (`LogID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `PhoneNumber` (`PhoneNumber`),
  ADD KEY `Email` (`Email`),
  ADD KEY `IP` (`IP`);

--
-- Indexes for table `tbl_users_session`
--
ALTER TABLE `tbl_users_session`
  ADD KEY `fk_DeviceTypeID` (`DeviceTypeID`),
  ADD KEY `fk_LoginSourceID` (`SourceID`),
  ADD KEY `fk_UserID` (`UserID`),
  ADD KEY `SessionKey` (`SessionKey`);

--
-- Indexes for table `tbl_users_settings`
--
ALTER TABLE `tbl_users_settings`
  ADD UNIQUE KEY `UserID` (`UserID`);

--
-- Indexes for table `tbl_users_type`
--
ALTER TABLE `tbl_users_type`
  ADD PRIMARY KEY (`UserTypeID`),
  ADD KEY `UserTypeName` (`UserTypeName`);

--
-- Indexes for table `tbl_users_wallet`
--
ALTER TABLE `tbl_users_wallet`
  ADD PRIMARY KEY (`WalletID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `StatusID` (`StatusID`),
  ADD KEY `EntityID` (`EntityID`),
  ADD KEY `UserTeamID` (`UserTeamID`);

--
-- Indexes for table `tbl_users_withdrawal`
--
ALTER TABLE `tbl_users_withdrawal`
  ADD PRIMARY KEY (`WithdrawalID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `StatusID` (`StatusID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_control`
--
ALTER TABLE `admin_control`
  MODIFY `ControlID` tinyint(2) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=117;

--
-- AUTO_INCREMENT for table `admin_modules`
--
ALTER TABLE `admin_modules`
  MODIFY `ModuleID` tinyint(2) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT for table `football_sports_contest_join`
--
ALTER TABLE `football_sports_contest_join`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `football_sports_pre_contest`
--
ALTER TABLE `football_sports_pre_contest`
  MODIFY `PreContestID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `football_sports_series_rounds`
--
ALTER TABLE `football_sports_series_rounds`
  MODIFY `RoundID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `football_sports_set_match_types`
--
ALTER TABLE `football_sports_set_match_types`
  MODIFY `MatchTypeID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `football_sports_team_players`
--
ALTER TABLE `football_sports_team_players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `logs_paytm`
--
ALTER TABLE `logs_paytm`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=161;

--
-- AUTO_INCREMENT for table `log_api`
--
ALTER TABLE `log_api`
  MODIFY `LogID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `log_cron`
--
ALTER TABLE `log_cron`
  MODIFY `CronID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `log_pushdata`
--
ALTER TABLE `log_pushdata`
  MODIFY `ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `set_categories_type`
--
ALTER TABLE `set_categories_type`
  MODIFY `CategoryTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `set_device_type`
--
ALTER TABLE `set_device_type`
  MODIFY `DeviceTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `set_pages`
--
ALTER TABLE `set_pages`
  MODIFY `PageID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `set_source`
--
ALTER TABLE `set_source`
  MODIFY `SourceID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `set_status`
--
ALTER TABLE `set_status`
  MODIFY `StatusID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `set_virtual_names`
--
ALTER TABLE `set_virtual_names`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sports_announcement`
--
ALTER TABLE `sports_announcement`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sports_pre_contest`
--
ALTER TABLE `sports_pre_contest`
  MODIFY `PreContestID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=211;

--
-- AUTO_INCREMENT for table `sports_series_rounds`
--
ALTER TABLE `sports_series_rounds`
  MODIFY `RoundID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `sports_set_match_types`
--
ALTER TABLE `sports_set_match_types`
  MODIFY `MatchTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `sports_team_players`
--
ALTER TABLE `sports_team_players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=94164;

--
-- AUTO_INCREMENT for table `sports_team_players_completed`
--
ALTER TABLE `sports_team_players_completed`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_auction_player_bid_status`
--
ALTER TABLE `tbl_auction_player_bid_status`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_broadcast_scheduling`
--
ALTER TABLE `tbl_broadcast_scheduling`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tbl_daily_invoice_report`
--
ALTER TABLE `tbl_daily_invoice_report`
  MODIFY `InvoiceID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31377;

--
-- AUTO_INCREMENT for table `tbl_entity`
--
ALTER TABLE `tbl_entity`
  MODIFY `EntityID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_entity_type`
--
ALTER TABLE `tbl_entity_type`
  MODIFY `EntityTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `tbl_lucky_wheel`
--
ALTER TABLE `tbl_lucky_wheel`
  MODIFY `PointsID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_lucky_wheel_transaction`
--
ALTER TABLE `tbl_lucky_wheel_transaction`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_notifications`
--
ALTER TABLE `tbl_notifications`
  MODIFY `NotificationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=427777;

--
-- AUTO_INCREMENT for table `tbl_notification_pattern`
--
ALTER TABLE `tbl_notification_pattern`
  MODIFY `NotificationPatternID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tbl_offers`
--
ALTER TABLE `tbl_offers`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=146;

--
-- AUTO_INCREMENT for table `tbl_referral_codes`
--
ALTER TABLE `tbl_referral_codes`
  MODIFY `ReferralCodeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77660;

--
-- AUTO_INCREMENT for table `tbl_subscription`
--
ALTER TABLE `tbl_subscription`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `tbl_test_razorpay`
--
ALTER TABLE `tbl_test_razorpay`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2526;

--
-- AUTO_INCREMENT for table `tbl_users_logs`
--
ALTER TABLE `tbl_users_logs`
  MODIFY `LogID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16763;

--
-- AUTO_INCREMENT for table `tbl_users_type`
--
ALTER TABLE `tbl_users_type`
  MODIFY `UserTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_users_wallet`
--
ALTER TABLE `tbl_users_wallet`
  MODIFY `WalletID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1153659;

--
-- AUTO_INCREMENT for table `tbl_users_withdrawal`
--
ALTER TABLE `tbl_users_withdrawal`
  MODIFY `WithdrawalID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=767;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin_control`
--
ALTER TABLE `admin_control`
  ADD CONSTRAINT `admin_control_ibfk_1` FOREIGN KEY (`ModuleID`) REFERENCES `admin_modules` (`ModuleID`) ON DELETE CASCADE;

--
-- Constraints for table `admin_user_type_permission`
--
ALTER TABLE `admin_user_type_permission`
  ADD CONSTRAINT `admin_user_type_permission_ibfk_1` FOREIGN KEY (`UserTypeID`) REFERENCES `tbl_users_type` (`UserTypeID`) ON DELETE CASCADE,
  ADD CONSTRAINT `admin_user_type_permission_ibfk_2` FOREIGN KEY (`ModuleID`) REFERENCES `admin_modules` (`ModuleID`) ON DELETE CASCADE;

--
-- Constraints for table `ecom_coupon`
--
ALTER TABLE `ecom_coupon`
  ADD CONSTRAINT `ecom_coupon_ibfk_3` FOREIGN KEY (`CouponID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `football_sports_contest`
--
ALTER TABLE `football_sports_contest`
  ADD CONSTRAINT `football_sports_contest_ibfk_3` FOREIGN KEY (`SeriesID`) REFERENCES `football_sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_contest_ibfk_5` FOREIGN KEY (`ContestID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_contest_ibfk_6` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_contest_ibfk_7` FOREIGN KEY (`MatchID`) REFERENCES `football_sports_matches` (`MatchID`) ON DELETE CASCADE ON UPDATE SET NULL,
  ADD CONSTRAINT `football_sports_contest_ibfk_8` FOREIGN KEY (`AuctionStatusID`) REFERENCES `set_status` (`StatusID`) ON DELETE CASCADE;

--
-- Constraints for table `football_sports_matches`
--
ALTER TABLE `football_sports_matches`
  ADD CONSTRAINT `football_sports_matches_ibfk_4` FOREIGN KEY (`SeriesID`) REFERENCES `football_sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_matches_ibfk_5` FOREIGN KEY (`TeamIDLocal`) REFERENCES `football_sports_teams` (`TeamID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_matches_ibfk_6` FOREIGN KEY (`TeamIDVisitor`) REFERENCES `football_sports_teams` (`TeamID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_matches_ibfk_7` FOREIGN KEY (`MatchTypeID`) REFERENCES `football_sports_set_match_types` (`MatchTypeID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_matches_ibfk_8` FOREIGN KEY (`MatchID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `football_sports_players`
--
ALTER TABLE `football_sports_players`
  ADD CONSTRAINT `football_sports_players_ibfk_1` FOREIGN KEY (`PlayerID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `football_sports_series`
--
ALTER TABLE `football_sports_series`
  ADD CONSTRAINT `football_sports_series_ibfk_1` FOREIGN KEY (`SeriesID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_series_ibfk_2` FOREIGN KEY (`AuctionDraftStatusID`) REFERENCES `set_status` (`StatusID`) ON DELETE CASCADE;

--
-- Constraints for table `football_sports_setting_points`
--
ALTER TABLE `football_sports_setting_points`
  ADD CONSTRAINT `football_sports_setting_points_ibfk_1` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`) ON DELETE CASCADE;

--
-- Constraints for table `football_sports_teams`
--
ALTER TABLE `football_sports_teams`
  ADD CONSTRAINT `football_sports_teams_ibfk_1` FOREIGN KEY (`TeamID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `football_sports_team_players`
--
ALTER TABLE `football_sports_team_players`
  ADD CONSTRAINT `football_sports_team_players_ibfk_2` FOREIGN KEY (`SeriesID`) REFERENCES `football_sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_team_players_ibfk_3` FOREIGN KEY (`TeamID`) REFERENCES `football_sports_teams` (`TeamID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_team_players_ibfk_4` FOREIGN KEY (`PlayerID`) REFERENCES `football_sports_players` (`PlayerID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_team_players_ibfk_5` FOREIGN KEY (`MatchID`) REFERENCES `football_sports_matches` (`MatchID`) ON DELETE CASCADE;

--
-- Constraints for table `football_sports_users_teams`
--
ALTER TABLE `football_sports_users_teams`
  ADD CONSTRAINT `football_sports_users_teams_contest_ibfk_5` FOREIGN KEY (`ContestID`) REFERENCES `football_sports_contest` (`ContestID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_users_teams_ibfk_2` FOREIGN KEY (`MatchID`) REFERENCES `football_sports_matches` (`MatchID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_users_teams_ibfk_3` FOREIGN KEY (`UserTeamID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_users_teams_ibfk_4` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `football_sports_users_teams_ibfk_5` FOREIGN KEY (`RoundID`) REFERENCES `football_sports_series_rounds` (`RoundID`) ON DELETE CASCADE;

--
-- Constraints for table `set_categories`
--
ALTER TABLE `set_categories`
  ADD CONSTRAINT `set_categories_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `set_categories_ibfk_2` FOREIGN KEY (`CategoryTypeID`) REFERENCES `set_categories_type` (`CategoryTypeID`) ON DELETE CASCADE;

--
-- Constraints for table `set_categories_type`
--
ALTER TABLE `set_categories_type`
  ADD CONSTRAINT `set_categories_type_ibfk_1` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`);

--
-- Constraints for table `set_site_config`
--
ALTER TABLE `set_site_config`
  ADD CONSTRAINT `set_site_config_ibfk_1` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`) ON DELETE CASCADE;

--
-- Constraints for table `social_post`
--
ALTER TABLE `social_post`
  ADD CONSTRAINT `social_post_ibfk_5` FOREIGN KEY (`ToEntityID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `social_post_ibfk_6` FOREIGN KEY (`PostID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `social_post_ibfk_7` FOREIGN KEY (`EntityID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `social_subscribers`
--
ALTER TABLE `social_subscribers`
  ADD CONSTRAINT `social_subscribers_ibfk_4` FOREIGN KEY (`ToEntityID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `social_subscribers_ibfk_5` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `social_subscribers_ibfk_6` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`);

--
-- Constraints for table `sports_auction_draft_player_point`
--
ALTER TABLE `sports_auction_draft_player_point`
  ADD CONSTRAINT `sports_auction_draft_player_point_ibfk1` FOREIGN KEY (`SeriesID`) REFERENCES `sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_auction_draft_player_point_ibfk3` FOREIGN KEY (`PlayerID`) REFERENCES `sports_players` (`PlayerID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_auction_draft_series_players`
--
ALTER TABLE `sports_auction_draft_series_players`
  ADD CONSTRAINT `sports_auction_draft_series_players_ibfk_2` FOREIGN KEY (`SeriesID`) REFERENCES `sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_auction_draft_series_players_ibfk_3` FOREIGN KEY (`TeamID`) REFERENCES `sports_teams` (`TeamID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_auction_draft_series_players_ibfk_4` FOREIGN KEY (`PlayerID`) REFERENCES `sports_players` (`PlayerID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_contest`
--
ALTER TABLE `sports_contest`
  ADD CONSTRAINT `sports_contest_ibfk_3` FOREIGN KEY (`SeriesID`) REFERENCES `sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_contest_ibfk_5` FOREIGN KEY (`ContestID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_contest_ibfk_6` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_contest_ibfk_7` FOREIGN KEY (`MatchID`) REFERENCES `sports_matches` (`MatchID`) ON DELETE CASCADE ON UPDATE SET NULL,
  ADD CONSTRAINT `sports_contest_ibfk_8` FOREIGN KEY (`AuctionStatusID`) REFERENCES `set_status` (`StatusID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_contest_completed`
--
ALTER TABLE `sports_contest_completed`
  ADD CONSTRAINT `sports_contest_completed_ibfk_3` FOREIGN KEY (`SeriesID`) REFERENCES `sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_contest_completed_ibfk_5` FOREIGN KEY (`ContestID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_contest_completed_ibfk_6` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_contest_completed_ibfk_7` FOREIGN KEY (`MatchID`) REFERENCES `sports_matches` (`MatchID`) ON DELETE CASCADE ON UPDATE SET NULL,
  ADD CONSTRAINT `sports_contest_completed_ibfk_8` FOREIGN KEY (`AuctionStatusID`) REFERENCES `set_status` (`StatusID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_matches`
--
ALTER TABLE `sports_matches`
  ADD CONSTRAINT `sports_matches_ibfk_4` FOREIGN KEY (`SeriesID`) REFERENCES `sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_matches_ibfk_5` FOREIGN KEY (`TeamIDLocal`) REFERENCES `sports_teams` (`TeamID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_matches_ibfk_6` FOREIGN KEY (`TeamIDVisitor`) REFERENCES `sports_teams` (`TeamID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_matches_ibfk_7` FOREIGN KEY (`MatchTypeID`) REFERENCES `sports_set_match_types` (`MatchTypeID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_matches_ibfk_8` FOREIGN KEY (`MatchID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_players`
--
ALTER TABLE `sports_players`
  ADD CONSTRAINT `sports_players_ibfk_1` FOREIGN KEY (`PlayerID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_series`
--
ALTER TABLE `sports_series`
  ADD CONSTRAINT `sports_series_ibfk_1` FOREIGN KEY (`SeriesID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_series_ibfk_2` FOREIGN KEY (`AuctionDraftStatusID`) REFERENCES `set_status` (`StatusID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_setting_points`
--
ALTER TABLE `sports_setting_points`
  ADD CONSTRAINT `sports_setting_points_ibfk_1` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_teams`
--
ALTER TABLE `sports_teams`
  ADD CONSTRAINT `sports_teams_ibfk_1` FOREIGN KEY (`TeamID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_team_players`
--
ALTER TABLE `sports_team_players`
  ADD CONSTRAINT `sports_team_players_ibfk_2` FOREIGN KEY (`SeriesID`) REFERENCES `sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_team_players_ibfk_3` FOREIGN KEY (`TeamID`) REFERENCES `sports_teams` (`TeamID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_team_players_ibfk_4` FOREIGN KEY (`PlayerID`) REFERENCES `sports_players` (`PlayerID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_team_players_ibfk_5` FOREIGN KEY (`MatchID`) REFERENCES `sports_matches` (`MatchID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_team_players_completed`
--
ALTER TABLE `sports_team_players_completed`
  ADD CONSTRAINT `sports_team_players_completed_ibfk_2` FOREIGN KEY (`SeriesID`) REFERENCES `sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_team_players_completed_ibfk_3` FOREIGN KEY (`TeamID`) REFERENCES `sports_teams` (`TeamID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_team_players_completed_ibfk_4` FOREIGN KEY (`PlayerID`) REFERENCES `sports_players` (`PlayerID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_team_players_completed_ibfk_5` FOREIGN KEY (`MatchID`) REFERENCES `sports_matches` (`MatchID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_users_teams`
--
ALTER TABLE `sports_users_teams`
  ADD CONSTRAINT `sports_users_teams_contest_ibfk_5` FOREIGN KEY (`ContestID`) REFERENCES `sports_contest` (`ContestID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_users_teams_ibfk_2` FOREIGN KEY (`MatchID`) REFERENCES `sports_matches` (`MatchID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_users_teams_ibfk_3` FOREIGN KEY (`UserTeamID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_users_teams_ibfk_4` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_users_teams_ibfk_5` FOREIGN KEY (`RoundID`) REFERENCES `sports_series_rounds` (`RoundID`) ON DELETE CASCADE;

--
-- Constraints for table `sports_users_teams_completed`
--
ALTER TABLE `sports_users_teams_completed`
  ADD CONSTRAINT `sports_users_teams_completed_ibfk_2` FOREIGN KEY (`MatchID`) REFERENCES `sports_matches` (`MatchID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_users_teams_completed_ibfk_3` FOREIGN KEY (`UserTeamID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_users_teams_completed_ibfk_4` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_users_teams_completed_ibfk_5` FOREIGN KEY (`RoundID`) REFERENCES `sports_series_rounds` (`RoundID`) ON DELETE CASCADE,
  ADD CONSTRAINT `sports_users_teams_temp_contest_ibfk_5` FOREIGN KEY (`ContestID`) REFERENCES `sports_contest` (`ContestID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_action`
--
ALTER TABLE `tbl_action`
  ADD CONSTRAINT `tbl_action_ibfk_1` FOREIGN KEY (`EntityID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_action_ibfk_2` FOREIGN KEY (`ToEntityID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_action_ibfk_3` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`);

--
-- Constraints for table `tbl_auction_player_bid`
--
ALTER TABLE `tbl_auction_player_bid`
  ADD CONSTRAINT `tbl_auction_player_bid_ibfk1` FOREIGN KEY (`SeriesID`) REFERENCES `sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_auction_player_bid_ibfk2` FOREIGN KEY (`ContestID`) REFERENCES `sports_contest` (`ContestID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_auction_player_bid_ibfk3` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_auction_player_bid_ibfk4` FOREIGN KEY (`PlayerID`) REFERENCES `sports_players` (`PlayerID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_auction_player_bid_status`
--
ALTER TABLE `tbl_auction_player_bid_status`
  ADD CONSTRAINT `tbl_auction_player_bid_status_ibfk1` FOREIGN KEY (`SeriesID`) REFERENCES `sports_series` (`SeriesID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_auction_player_bid_status_ibfk2` FOREIGN KEY (`ContestID`) REFERENCES `sports_contest` (`ContestID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_auction_player_bid_status_ibfk3` FOREIGN KEY (`PlayerID`) REFERENCES `sports_players` (`PlayerID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_entity`
--
ALTER TABLE `tbl_entity`
  ADD CONSTRAINT `tbl_entity_ibfk_3` FOREIGN KEY (`EntityTypeID`) REFERENCES `tbl_entity_type` (`EntityTypeID`),
  ADD CONSTRAINT `tbl_entity_ibfk_4` FOREIGN KEY (`CreatedByUserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_entity_ibfk_5` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`);

--
-- Constraints for table `tbl_entity_categories`
--
ALTER TABLE `tbl_entity_categories`
  ADD CONSTRAINT `tbl_entity_categories_ibfk_2` FOREIGN KEY (`CategoryID`) REFERENCES `set_categories` (`CategoryID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_entity_categories_ibfk_3` FOREIGN KEY (`EntityID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_entity_views`
--
ALTER TABLE `tbl_entity_views`
  ADD CONSTRAINT `tbl_entity_views_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_entity_views_ibfk_2` FOREIGN KEY (`EntityID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_media`
--
ALTER TABLE `tbl_media`
  ADD CONSTRAINT `tbl_media_ibfk_3` FOREIGN KEY (`MediaID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_media_ibfk_4` FOREIGN KEY (`EntityID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_media_ibfk_5` FOREIGN KEY (`SectionID`) REFERENCES `tbl_media_sections` (`SectionID`);

--
-- Constraints for table `tbl_notifications`
--
ALTER TABLE `tbl_notifications`
  ADD CONSTRAINT `tbl_notifications_ibfk_3` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_notifications_ibfk_4` FOREIGN KEY (`ToUserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_notifications_ibfk_5` FOREIGN KEY (`NotificationPatternID`) REFERENCES `tbl_notification_pattern` (`NotificationPatternID`),
  ADD CONSTRAINT `tbl_notifications_ibfk_6` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`);

--
-- Constraints for table `tbl_offers`
--
ALTER TABLE `tbl_offers`
  ADD CONSTRAINT `tbl_offers_ibfk_1` FOREIGN KEY (`MatchID`) REFERENCES `sports_matches` (`MatchID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_referral_codes`
--
ALTER TABLE `tbl_referral_codes`
  ADD CONSTRAINT `tbl_referral_codes_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE SET NULL,
  ADD CONSTRAINT `tbl_referral_codes_ibfk_3` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`);

--
-- Constraints for table `tbl_tokens`
--
ALTER TABLE `tbl_tokens`
  ADD CONSTRAINT `tbl_tokens_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_tokens_ibfk_3` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`);

--
-- Constraints for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD CONSTRAINT `tbl_users_ibfk_3` FOREIGN KEY (`UserTypeID`) REFERENCES `tbl_users_type` (`UserTypeID`),
  ADD CONSTRAINT `tbl_users_ibfk_4` FOREIGN KEY (`UserID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_users_ibfk_5` FOREIGN KEY (`ReferralCodeID`) REFERENCES `tbl_referral_codes` (`ReferralCodeID`),
  ADD CONSTRAINT `tbl_users_ibfk_6` FOREIGN KEY (`ReferredByUserID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE SET NULL;

--
-- Constraints for table `tbl_users_login`
--
ALTER TABLE `tbl_users_login`
  ADD CONSTRAINT `tbl_users_login_ibfk_2` FOREIGN KEY (`SourceID`) REFERENCES `set_source` (`SourceID`),
  ADD CONSTRAINT `tbl_users_login_ibfk_3` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_users_session`
--
ALTER TABLE `tbl_users_session`
  ADD CONSTRAINT `tbl_users_session_ibfk_1` FOREIGN KEY (`DeviceTypeID`) REFERENCES `set_device_type` (`DeviceTypeID`),
  ADD CONSTRAINT `tbl_users_session_ibfk_2` FOREIGN KEY (`SourceID`) REFERENCES `set_source` (`SourceID`),
  ADD CONSTRAINT `tbl_users_session_ibfk_3` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_users_settings`
--
ALTER TABLE `tbl_users_settings`
  ADD CONSTRAINT `tbl_users_settings_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_users_wallet`
--
ALTER TABLE `tbl_users_wallet`
  ADD CONSTRAINT `tbl_users_wallet_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_users_wallet_ibfk_2` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`),
  ADD CONSTRAINT `tbl_users_wallet_ibfk_3` FOREIGN KEY (`EntityID`) REFERENCES `tbl_entity` (`EntityID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_users_withdrawal`
--
ALTER TABLE `tbl_users_withdrawal`
  ADD CONSTRAINT `tbl_users_withdrawal_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `tbl_users` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_users_withdrawal_ibfk_2` FOREIGN KEY (`StatusID`) REFERENCES `set_status` (`StatusID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
