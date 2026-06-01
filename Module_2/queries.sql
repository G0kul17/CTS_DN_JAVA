-- USE community_portal;
USE community_portal;

-- ============================================================
-- Exercise 1: User Upcoming Events
-- Purpose: List all upcoming events a user is registered for, in their city
-- Tables: Events, Registrations, Users
-- Key SQL: Multi-table JOIN, WHERE filtering, ORDER BY
-- ============================================================
SELECT
  e.event_id,
  e.title          AS event_title,
  e.city,
  e.start_date,
  e.end_date,
  e.status
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
JOIN Users u         ON r.user_id  = u.user_id
WHERE e.status   = 'upcoming'
  AND r.user_id  = 1
  AND e.city     = u.city
ORDER BY e.start_date ASC;

-- ============================================================
-- Exercise 2: Top Rated Events
-- Purpose: Events with highest average rating, only where feedback count >= 10
-- Tables: Feedback, Events
-- Key SQL: GROUP BY, HAVING, AVG, COUNT, ROUND
-- ============================================================
SELECT
  e.event_id,
  e.title                           AS event_title,
  ROUND(AVG(f.rating), 2)           AS avg_rating,
  COUNT(f.feedback_id)              AS feedback_count
FROM Feedback f
JOIN Events e ON f.event_id = e.event_id
GROUP BY f.event_id, e.event_id, e.title
HAVING COUNT(f.feedback_id) >= 10
ORDER BY avg_rating DESC;

-- ============================================================
-- Exercise 3: Inactive Users
-- Purpose: Users who have NOT registered for any event in the last 90 days
-- Tables: Users, Registrations
-- Key SQL: LEFT JOIN, GROUP BY, HAVING, CURDATE(), INTERVAL
-- ============================================================
SELECT
  u.user_id,
  u.full_name,
  u.email,
  u.city,
  MAX(r.registration_date) AS last_registration_date
FROM Users u
LEFT JOIN Registrations r ON u.user_id = r.user_id
GROUP BY u.user_id, u.full_name, u.email, u.city
HAVING MAX(r.registration_date) IS NULL
    OR MAX(r.registration_date) < CURDATE() - INTERVAL 90 DAY
ORDER BY last_registration_date ASC;

-- ============================================================
-- Exercise 4: Peak Session Hours
-- Purpose: Count sessions scheduled between 10:00 AM and 12:00 PM per event
-- Tables: Sessions, Events
-- Key SQL: TIME(), WHERE range filter, GROUP BY, COUNT
-- ============================================================
SELECT
  e.event_id,
  e.title              AS event_title,
  COUNT(s.session_id)  AS peak_hour_sessions
FROM Sessions s
JOIN Events e ON s.event_id = e.event_id
WHERE TIME(s.start_time) >= '10:00:00'
  AND TIME(s.start_time) <  '12:00:00'
GROUP BY s.event_id, e.event_id, e.title
ORDER BY peak_hour_sessions DESC;

-- ============================================================
-- Exercise 5: Most Active Cities
-- Purpose: Top 5 cities by distinct user registrations
-- Tables: Users, Registrations
-- Key SQL: COUNT DISTINCT, GROUP BY, ORDER BY, LIMIT
-- ============================================================
SELECT
  u.city,
  COUNT(DISTINCT r.user_id) AS distinct_registered_users
FROM Users u
JOIN Registrations r ON u.user_id = r.user_id
GROUP BY u.city
ORDER BY distinct_registered_users DESC
LIMIT 5;

-- ============================================================
-- Exercise 6: Event Resource Summary
-- Purpose: Resources per event broken down by type using conditional aggregation
-- Tables: Events, Resources
-- Key SQL: LEFT JOIN, SUM(CASE WHEN), conditional aggregation
-- ============================================================
SELECT
  e.event_id,
  e.title                                                          AS event_title,
  SUM(CASE WHEN r.resource_type = 'pdf'   THEN 1 ELSE 0 END)      AS pdf_count,
  SUM(CASE WHEN r.resource_type = 'image' THEN 1 ELSE 0 END)      AS image_count,
  SUM(CASE WHEN r.resource_type = 'link'  THEN 1 ELSE 0 END)      AS link_count,
  COUNT(r.resource_id)                                             AS total_resources
FROM Events e
LEFT JOIN Resources r ON e.event_id = r.event_id
GROUP BY e.event_id, e.title
ORDER BY total_resources DESC;

-- ============================================================
-- Exercise 7: Low Feedback Alerts
-- Purpose: Users who gave a rating < 3, with their comments and event name
-- Tables: Feedback, Users, Events
-- Key SQL: Multi-table JOIN, WHERE rating filter, ORDER BY
-- ============================================================
SELECT
  u.full_name     AS user_name,
  u.email,
  f.rating,
  f.comments,
  e.title         AS event_name,
  f.feedback_date
FROM Feedback f
JOIN Users  u ON f.user_id  = u.user_id
JOIN Events e ON f.event_id = e.event_id
WHERE f.rating < 3
ORDER BY f.rating ASC, f.feedback_date DESC;

-- ============================================================
-- Exercise 8: Sessions per Upcoming Event
-- Purpose: Upcoming events with their session count
-- Tables: Events, Sessions
-- Key SQL: JOIN, WHERE status filter, GROUP BY, COUNT
-- ============================================================
SELECT
  e.event_id,
  e.title              AS event_title,
  e.city,
  e.start_date,
  COUNT(s.session_id)  AS session_count
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
WHERE e.status = 'upcoming'
GROUP BY e.event_id, e.title, e.city, e.start_date
ORDER BY session_count DESC;

-- ============================================================
-- Exercise 9: Organizer Event Summary
-- Purpose: Per organizer, number of events grouped by status
-- Tables: Users, Events
-- Key SQL: JOIN, GROUP BY multiple columns, ORDER BY
-- ============================================================
SELECT
  u.user_id                AS organizer_id,
  u.full_name              AS organizer_name,
  e.status,
  COUNT(e.event_id)        AS event_count
FROM Users u
JOIN Events e ON u.user_id = e.organizer_id
GROUP BY u.user_id, u.full_name, e.status
ORDER BY u.user_id, e.status;

-- ============================================================
-- Exercise 10: Feedback Gap
-- Purpose: Events with at least 1 registration but zero feedback
-- Tables: Events, Registrations, Feedback
-- Key SQL: JOIN + LEFT JOIN, HAVING COUNT = 0, anti-pattern detection
-- ============================================================
SELECT
  e.event_id,
  e.title                            AS event_title,
  e.status,
  COUNT(DISTINCT r.registration_id)  AS registration_count,
  COUNT(f.feedback_id)               AS feedback_count
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
LEFT JOIN Feedback f ON e.event_id = f.event_id
GROUP BY e.event_id, e.title, e.status
HAVING COUNT(f.feedback_id) = 0
ORDER BY registration_count DESC;

-- ============================================================
-- Exercise 11: Daily New User Count
-- Purpose: Number of new users registered each day in the last 7 days
-- Tables: Users
-- Key SQL: WHERE date range, GROUP BY date, COUNT
-- ============================================================
SELECT
  registration_date,
  COUNT(user_id) AS new_users
FROM Users
WHERE registration_date >= CURDATE() - INTERVAL 7 DAY
GROUP BY registration_date
ORDER BY registration_date ASC;

-- ============================================================
-- Exercise 12: Event with Maximum Sessions
-- Purpose: Event(s) with the highest session count, handling ties
-- Tables: Events, Sessions
-- Key SQL: Correlated subquery, MAX in subquery, HAVING
-- ============================================================
SELECT
  e.event_id,
  e.title             AS event_title,
  COUNT(s.session_id) AS session_count
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
GROUP BY e.event_id, e.title
HAVING COUNT(s.session_id) = (
  SELECT MAX(cnt)
  FROM (
    SELECT COUNT(session_id) AS cnt
    FROM Sessions
    GROUP BY event_id
  ) AS session_counts
)
ORDER BY e.event_id;

-- ============================================================
-- Exercise 13: Average Rating per City
-- Purpose: Average feedback rating grouped by the city where the event was held
-- Tables: Feedback, Events
-- Key SQL: JOIN, GROUP BY city, AVG, ROUND
-- ============================================================
SELECT
  e.city,
  ROUND(AVG(f.rating), 2)  AS avg_rating,
  COUNT(f.feedback_id)     AS total_feedback
FROM Feedback f
JOIN Events e ON f.event_id = e.event_id
GROUP BY e.city
ORDER BY avg_rating DESC;

-- ============================================================
-- Exercise 14: Most Registered Events
-- Purpose: Top 3 events by total registration count
-- Tables: Registrations, Events
-- Key SQL: JOIN, GROUP BY, COUNT, ORDER BY DESC, LIMIT
-- ============================================================
SELECT
  e.event_id,
  e.title                      AS event_title,
  e.status,
  COUNT(r.registration_id)     AS registration_count
FROM Registrations r
JOIN Events e ON r.event_id = e.event_id
GROUP BY r.event_id, e.event_id, e.title, e.status
ORDER BY registration_count DESC
LIMIT 3;

-- ============================================================
-- Exercise 15: Session Time Conflict
-- Purpose: Sessions within the same event where times overlap
-- Tables: Sessions, Events
-- Key SQL: Self-join, overlap condition (s1.start < s2.end AND s1.end > s2.start)
-- ============================================================
SELECT
  s1.event_id,
  e.title              AS event_title,
  s1.session_id        AS session_a_id,
  s1.title             AS session_a_title,
  s1.start_time        AS a_start,
  s1.end_time          AS a_end,
  s2.session_id        AS session_b_id,
  s2.title             AS session_b_title,
  s2.start_time        AS b_start,
  s2.end_time          AS b_end
FROM Sessions s1
JOIN Sessions s2
  ON s1.event_id    = s2.event_id
 AND s1.session_id  < s2.session_id
 AND s1.start_time  < s2.end_time
 AND s1.end_time    > s2.start_time
JOIN Events e ON s1.event_id = e.event_id
ORDER BY s1.event_id, s1.session_id;

-- ============================================================
-- Exercise 16: Unregistered Active Users
-- Purpose: Users registered in the last 30 days who haven't signed up for any event
-- Tables: Users, Registrations
-- Key SQL: LEFT JOIN, WHERE IS NULL (anti-join), date range filter
-- ============================================================
SELECT
  u.user_id,
  u.full_name,
  u.email,
  u.city,
  u.registration_date
FROM Users u
LEFT JOIN Registrations r ON u.user_id = r.user_id
WHERE u.registration_date >= CURDATE() - INTERVAL 30 DAY
  AND r.user_id IS NULL
ORDER BY u.registration_date DESC;

-- ============================================================
-- Exercise 17: Multi-Session Speakers
-- Purpose: Speakers handling more than one session across all events
-- Tables: Sessions
-- Key SQL: GROUP BY speaker_name, HAVING COUNT > 1, GROUP_CONCAT
-- ============================================================
SELECT
  speaker_name,
  COUNT(session_id)  AS session_count,
  GROUP_CONCAT(title ORDER BY start_time SEPARATOR ' | ') AS sessions_handled
FROM Sessions
GROUP BY speaker_name
HAVING COUNT(session_id) > 1
ORDER BY session_count DESC;

-- ============================================================
-- Exercise 18: Resource Availability Check
-- Purpose: Events that have no resources uploaded
-- Tables: Events, Resources
-- Key SQL: LEFT JOIN, WHERE IS NULL (anti-join pattern)
-- ============================================================
SELECT
  e.event_id,
  e.title    AS event_title,
  e.status,
  e.city,
  e.start_date
FROM Events e
LEFT JOIN Resources r ON e.event_id = r.event_id
WHERE r.resource_id IS NULL
ORDER BY e.start_date;

-- ============================================================
-- Exercise 19: Completed Events with Feedback Summary
-- Purpose: Completed events showing total registrations and average feedback rating
-- Tables: Events, Registrations, Feedback
-- Key SQL: Multiple LEFT JOINs, COUNT DISTINCT, AVG, WHERE status filter
-- ============================================================
SELECT
  e.event_id,
  e.title                            AS event_title,
  COUNT(DISTINCT r.registration_id)  AS total_registrations,
  COUNT(DISTINCT f.feedback_id)      AS total_feedback,
  ROUND(AVG(f.rating), 2)            AS avg_rating
FROM Events e
LEFT JOIN Registrations r ON e.event_id = r.event_id
LEFT JOIN Feedback f      ON e.event_id = f.event_id
WHERE e.status = 'completed'
GROUP BY e.event_id, e.title
ORDER BY avg_rating DESC;

-- ============================================================
-- Exercise 20: User Engagement Index
-- Purpose: Per user, count of events registered plus count of feedbacks submitted
-- Tables: Users, Registrations, Feedback
-- Key SQL: Multiple LEFT JOINs, COUNT DISTINCT, computed engagement_index column
-- ============================================================
SELECT
  u.user_id,
  u.full_name,
  u.email,
  COUNT(DISTINCT r.registration_id)                AS events_registered,
  COUNT(DISTINCT f.feedback_id)                    AS feedbacks_submitted,
  COUNT(DISTINCT r.registration_id) +
  COUNT(DISTINCT f.feedback_id)                    AS engagement_index
FROM Users u
LEFT JOIN Registrations r ON u.user_id = r.user_id
LEFT JOIN Feedback      f ON u.user_id = f.user_id
GROUP BY u.user_id, u.full_name, u.email
ORDER BY engagement_index DESC;

-- ============================================================
-- Exercise 21: Top Feedback Providers
-- Purpose: Top 5 users who submitted the most feedback entries
-- Tables: Feedback, Users
-- Key SQL: JOIN, GROUP BY, COUNT, AVG, LIMIT
-- ============================================================
SELECT
  u.user_id,
  u.full_name,
  u.email,
  COUNT(f.feedback_id)           AS feedback_count,
  ROUND(AVG(f.rating), 2)        AS avg_rating_given
FROM Feedback f
JOIN Users u ON f.user_id = u.user_id
GROUP BY f.user_id, u.user_id, u.full_name, u.email
ORDER BY feedback_count DESC
LIMIT 5;

-- ============================================================
-- Exercise 22: Duplicate Registrations Check
-- Purpose: Detect users registered more than once for the same event
-- Tables: Registrations, Users, Events
-- Key SQL: GROUP BY composite key, HAVING COUNT > 1, data quality check
-- ============================================================
SELECT
  r.user_id,
  u.full_name,
  r.event_id,
  e.title        AS event_title,
  COUNT(*)       AS duplicate_count
FROM Registrations r
JOIN Users  u ON r.user_id  = u.user_id
JOIN Events e ON r.event_id = e.event_id
GROUP BY r.user_id, r.event_id, u.full_name, e.title
HAVING COUNT(*) > 1
ORDER BY duplicate_count DESC;

-- ============================================================
-- Exercise 23: Registration Trends
-- Purpose: Month-wise registration count over the past 12 months
-- Tables: Registrations
-- Key SQL: DATE_FORMAT, GROUP BY formatted date, WHERE date range
-- ============================================================
SELECT
  DATE_FORMAT(registration_date, '%Y-%m')  AS month,
  COUNT(registration_id)                   AS registration_count
FROM Registrations
WHERE registration_date >= CURDATE() - INTERVAL 12 MONTH
GROUP BY DATE_FORMAT(registration_date, '%Y-%m')
ORDER BY month ASC;

-- ============================================================
-- Exercise 24: Average Session Duration per Event
-- Purpose: Average duration in minutes of sessions per event
-- Tables: Sessions, Events
-- Key SQL: TIMESTAMPDIFF(MINUTE), AVG, MIN, MAX, ROUND
-- ============================================================
SELECT
  e.event_id,
  e.title                                                         AS event_title,
  COUNT(s.session_id)                                             AS session_count,
  ROUND(AVG(TIMESTAMPDIFF(MINUTE, s.start_time, s.end_time)), 2) AS avg_duration_minutes,
  MIN(TIMESTAMPDIFF(MINUTE, s.start_time, s.end_time))           AS min_duration_minutes,
  MAX(TIMESTAMPDIFF(MINUTE, s.start_time, s.end_time))           AS max_duration_minutes
FROM Sessions s
JOIN Events e ON s.event_id = e.event_id
GROUP BY s.event_id, e.event_id, e.title
ORDER BY avg_duration_minutes DESC;

-- ============================================================
-- Exercise 25: Events Without Sessions
-- Purpose: Events that have no sessions scheduled
-- Tables: Events, Sessions
-- Key SQL: LEFT JOIN, WHERE IS NULL (anti-join pattern)
-- ============================================================
SELECT
  e.event_id,
  e.title      AS event_title,
  e.status,
  e.city,
  e.start_date
FROM Events e
LEFT JOIN Sessions s ON e.event_id = s.event_id
WHERE s.session_id IS NULL
ORDER BY e.start_date;
