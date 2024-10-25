DROP DATABASE ssafydb;
CREATE DATABASE IF NOT EXISTS ssafydb;
USE ssafydb;

CREATE TABLE `user`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nickname` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255),
    `profile_img` VARCHAR(255),
    `tier` VARCHAR(255) NOT NULL,
    `exp` BIGINT NOT NULL
);
-- user 객체에 목업 데이터 삽입
INSERT INTO `user` (`nickname`, `email`, `profile_img`, `tier`, `exp`)
VALUES 
    ('Icarus', 'email_example1@gmail.com', 'img_example1.jpg', 'BRONZE', 1000),
    ('Jay', 'email_example2@gmail.com', 'img_example2.jpg', 'MASTER', 2000),
    ('Ollie', 'email_example3@gmail.com', 'img_example3.jpg', 'DIAMOND', 3000);
SELECT * FROM user;

CREATE TABLE `challenge`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `status` VARCHAR(255) NOT NULL,
    `type` VARCHAR(255) NOT NULL,
    `start_dt` DATE NOT NULL,
    `end_dt` DATE NOT NULL,
    `goal_cnt` BIGINT NOT NULL,
    `achieve_cnt` INT NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL,
    CONSTRAINT `challenge_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);
-- challenge 객체에 목업 데이터 삽입
INSERT INTO `challenge` (`status`, `type`, `start_dt`, `end_dt`, `goal_cnt`, `achieve_cnt`, `user_id`)
VALUES 
    ('PENDING', 'SQUAT', '2024-01-01', '2024-01-31', 50, 1000, 1),
    ('ONGOING', 'PUSHUP', '2024-02-01', '2024-02-28', 30, 989, 2),
    ('DONE', 'SQUAT', '2024-03-01', '2024-03-31', 20, 782, 3);
SELECT * FROM challenge;

CREATE TABLE `game`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `date` DATE NOT NULL,
    `type` VARCHAR(255) NOT NULL,
    `difficulty_level` VARCHAR(255) NOT NULL,
    `is_achieved` BOOLEAN NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL,
    CONSTRAINT `game_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);
-- game 객체에 목업 데이터 삽입
INSERT INTO `game` (`date`, `type`, `difficulty_level`, `is_achieved`, `user_id`)
VALUES 
    ('2024-04-01', 'SQUAT', 'EASY', true, 1),
    ('2024-04-02', 'PUSHUP', 'MEDIUM', false, 2),
    ('2024-04-03', 'PUSHUP', 'HARD', true, 3);
SELECT * FROM game;

CREATE TABLE `today_challenge`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `cnt` BIGINT NOT NULL,
    `challenge_id` BIGINT UNSIGNED NOT NULL,
    CONSTRAINT `today_challenge_challenge_id_foreign` FOREIGN KEY (`challenge_id`) REFERENCES `challenge` (`id`)
);
-- today_challenge 객체에 목업 데이터 삽입
INSERT INTO `today_challenge` (`cnt`, `challenge_id`)
VALUES 
    (15, 1),
    (10, 2),
    (21, 3);
SELECT * FROM today_challenge;

CREATE TABLE `attendance`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `attend_dt` DATE NOT NULL,
    `is_attended` BOOLEAN NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL,
    CONSTRAINT `attendance_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);
-- attendance 객체에 목업 데이터 삽입
INSERT INTO `attendance` (`attend_dt`, `is_attended`, `user_id`)
VALUES 
    ('2024-04-01', true, 1),
    ('2024-04-02', false, 2),
    ('2024-04-03', true, 3);
SELECT * FROM attendance;
