-- ============================================================
-- File    : schema.sql
-- Module  : Module 2 – Local Community Event Portal
-- Author  : CTS Project Team
-- Created : 2026-06-01
-- Purpose : Defines the complete relational schema for the
--           community_portal database, including all tables,
--           foreign-key relationships, sample seed data, and
--           a row-count verification query.
-- ============================================================


-- ============================================================
-- DATABASE SETUP
-- Drop (if it already exists) and recreate the database to
-- ensure a clean, reproducible environment.
-- utf8mb4 is used for full Unicode support (including emoji).
-- ============================================================

DROP DATABASE IF EXISTS community_portal;

CREATE DATABASE community_portal
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE community_portal;


-- ============================================================
-- TABLE: Users
-- Stores registered members of the community portal.
-- Every user must supply a unique e-mail address and the city
-- they are located in.  The registration_date records when
-- the account was created.
-- ============================================================

CREATE TABLE Users (
  user_id           INT          PRIMARY KEY AUTO_INCREMENT,
  full_name         VARCHAR(100) NOT NULL,
  email             VARCHAR(100) UNIQUE NOT NULL,
  city              VARCHAR(100) NOT NULL,
  registration_date DATE         NOT NULL
);


-- ============================================================
-- TABLE: Events
-- Represents community events organised through the portal.
-- Each event is associated with an organiser (a User) via the
-- organizer_id foreign key.  The status column tracks the
-- life-cycle of an event: upcoming → completed | cancelled.
-- ============================================================

CREATE TABLE Events (
  event_id     INT          PRIMARY KEY AUTO_INCREMENT,
  title        VARCHAR(200) NOT NULL,
  description  TEXT,
  city         VARCHAR(100) NOT NULL,
  start_date   DATETIME     NOT NULL,
  end_date     DATETIME     NOT NULL,
  status       ENUM('upcoming','completed','cancelled') NOT NULL,
  organizer_id INT,
  FOREIGN KEY (organizer_id) REFERENCES Users(user_id)
);


-- ============================================================
-- TABLE: Sessions
-- Captures individual talks / workshops that are scheduled
-- within an Event.  A single Event can have many Sessions.
-- speaker_name stores the presenter's display name.
-- ============================================================

CREATE TABLE Sessions (
  session_id   INT          PRIMARY KEY AUTO_INCREMENT,
  event_id     INT,
  title        VARCHAR(200) NOT NULL,
  speaker_name VARCHAR(100) NOT NULL,
  start_time   DATETIME     NOT NULL,
  end_time     DATETIME     NOT NULL,
  FOREIGN KEY (event_id) REFERENCES Events(event_id)
);


-- ============================================================
-- TABLE: Registrations
-- Records which Users have signed up for which Events.
-- The combination of user_id + event_id implicitly represents
-- a user's participation intent.  registration_date is the
-- calendar date on which the sign-up was completed.
-- ============================================================

CREATE TABLE Registrations (
  registration_id   INT  PRIMARY KEY AUTO_INCREMENT,
  user_id           INT,
  event_id          INT,
  registration_date DATE NOT NULL,
  FOREIGN KEY (user_id)  REFERENCES Users(user_id),
  FOREIGN KEY (event_id) REFERENCES Events(event_id)
);


-- ============================================================
-- TABLE: Feedback
-- Allows attendees to rate and comment on Events they attended.
-- rating is constrained to 1–5 (enforced via CHECK constraint).
-- feedback_date is the date the review was submitted.
-- ============================================================

CREATE TABLE Feedback (
  feedback_id   INT  PRIMARY KEY AUTO_INCREMENT,
  user_id       INT,
  event_id      INT,
  rating        INT  CHECK (rating BETWEEN 1 AND 5),
  comments      TEXT,
  feedback_date DATE NOT NULL,
  FOREIGN KEY (user_id)  REFERENCES Users(user_id),
  FOREIGN KEY (event_id) REFERENCES Events(event_id)
);


-- ============================================================
-- TABLE: Resources
-- Stores downloadable / viewable assets attached to Events.
-- resource_type distinguishes between PDFs, images, and links.
-- resource_url holds the publicly accessible URL of the asset.
-- uploaded_at records the exact timestamp of the upload.
-- ============================================================

CREATE TABLE Resources (
  resource_id   INT          PRIMARY KEY AUTO_INCREMENT,
  event_id      INT,
  resource_type ENUM('pdf','image','link') NOT NULL,
  resource_url  VARCHAR(255) NOT NULL,
  uploaded_at   DATETIME     NOT NULL,
  FOREIGN KEY (event_id) REFERENCES Events(event_id)
);


-- ============================================================
-- SAMPLE DATA – Users
-- Five community members spread across New York, Los Angeles,
-- and Chicago who registered between December 2024 and
-- February 2025.
-- ============================================================

INSERT INTO Users VALUES
(1, 'Alice Johnson', 'alice@example.com',   'New York',    '2024-12-01'),
(2, 'Bob Smith',     'bob@example.com',     'Los Angeles', '2024-12-05'),
(3, 'Charlie Lee',   'charlie@example.com', 'Chicago',     '2024-12-10'),
(4, 'Diana King',    'diana@example.com',   'New York',    '2025-01-15'),
(5, 'Ethan Hunt',    'ethan@example.com',   'Los Angeles', '2025-02-01');


-- ============================================================
-- SAMPLE DATA – Events
-- Three events spanning New York, Chicago, and Los Angeles.
-- Alice (user 1) organises the Tech Innovators Meetup,
-- Charlie (user 3) organises the AI & ML Conference, and
-- Bob (user 2) organises the Frontend Development Bootcamp.
-- ============================================================

INSERT INTO Events VALUES
(1, 'Tech Innovators Meetup',        'A meetup for tech enthusiasts.',          'New York',    '2025-06-10 10:00:00', '2025-06-10 16:00:00', 'upcoming',   1),
(2, 'AI & ML Conference',            'Conference on AI and ML advancements.',   'Chicago',     '2025-05-15 09:00:00', '2025-05-15 17:00:00', 'completed',  3),
(3, 'Frontend Development Bootcamp', 'Hands-on training on frontend tech.',     'Los Angeles', '2025-07-01 10:00:00', '2025-07-03 16:00:00', 'upcoming',   2);


-- ============================================================
-- SAMPLE DATA – Sessions
-- Four sessions distributed across the three events above.
-- Sessions for Event 1 (Tech Innovators Meetup):
--   • Opening Keynote by Dr. Tech
--   • Future of Web Dev by Alice Johnson
-- Session for Event 2 (AI & ML Conference):
--   • AI in Healthcare by Charlie Lee
-- Session for Event 3 (Frontend Bootcamp):
--   • Intro to HTML5 by Bob Smith
-- ============================================================

INSERT INTO Sessions VALUES
(1, 1, 'Opening Keynote',    'Dr. Tech',      '2025-06-10 10:00:00', '2025-06-10 11:00:00'),
(2, 1, 'Future of Web Dev',  'Alice Johnson', '2025-06-10 11:15:00', '2025-06-10 12:30:00'),
(3, 2, 'AI in Healthcare',   'Charlie Lee',   '2025-05-15 09:30:00', '2025-05-15 11:00:00'),
(4, 3, 'Intro to HTML5',     'Bob Smith',     '2025-07-01 10:00:00', '2025-07-01 12:00:00');


-- ============================================================
-- SAMPLE DATA – Registrations
-- Five registrations linking users to the events they plan
-- to or have attended:
--   • Alice & Bob registered for Event 1 (Tech Meetup)
--   • Charlie & Diana registered for Event 2 (AI & ML Conf.)
--   • Ethan registered for Event 3 (Frontend Bootcamp)
-- ============================================================

INSERT INTO Registrations VALUES
(1, 1, 1, '2025-05-01'),
(2, 2, 1, '2025-05-02'),
(3, 3, 2, '2025-04-30'),
(4, 4, 2, '2025-04-28'),
(5, 5, 3, '2025-06-15');


-- ============================================================
-- SAMPLE DATA – Feedback
-- Three post-event reviews submitted for completed / attended
-- events:
--   • Charlie rated Event 2 → 4/5 ("Great insights!")
--   • Diana rated Event 2  → 5/5 ("Very informative.")
--   • Bob rated Event 1    → 3/5 ("Could be better.")
-- ============================================================

INSERT INTO Feedback VALUES
(1, 3, 2, 4, 'Great insights!',    '2025-05-16'),
(2, 4, 2, 5, 'Very informative.',  '2025-05-16'),
(3, 2, 1, 3, 'Could be better.',   '2025-06-11');


-- ============================================================
-- SAMPLE DATA – Resources
-- Three assets attached to events:
--   • A PDF agenda for the Tech Innovators Meetup (Event 1)
--   • A promotional image for the AI & ML Conference (Event 2)
--   • A documentation link for the Frontend Bootcamp (Event 3)
-- ============================================================

INSERT INTO Resources VALUES
(1, 1, 'pdf',   'https://portal.com/resources/tech_meetup_agenda.pdf', '2025-05-01 10:00:00'),
(2, 2, 'image', 'https://portal.com/resources/ai_poster.jpg',          '2025-04-20 09:00:00'),
(3, 3, 'link',  'https://portal.com/resources/html5_docs',             '2025-06-25 15:00:00');


-- ============================================================
-- Verification: row counts per table
-- Run this query after executing the script to confirm that
-- all INSERT statements loaded the expected number of rows.
-- Expected output:
--   Users         → 5
--   Events        → 3
--   Sessions      → 4
--   Registrations → 5
--   Feedback      → 3
--   Resources     → 3
-- ============================================================

SELECT 'Users'         AS table_name, COUNT(*) AS row_count FROM Users
UNION ALL
SELECT 'Events',        COUNT(*) FROM Events
UNION ALL
SELECT 'Sessions',      COUNT(*) FROM Sessions
UNION ALL
SELECT 'Registrations', COUNT(*) FROM Registrations
UNION ALL
SELECT 'Feedback',      COUNT(*) FROM Feedback
UNION ALL
SELECT 'Resources',     COUNT(*) FROM Resources;
