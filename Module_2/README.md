# Local Community Event Portal — SQL Module (Module 2)

MySQL database schema and exercise queries for the Community Event Portal.  
Covers database design, joins, aggregation, subqueries, date functions, and more.

---

## How to Run

### Prerequisites
- MySQL 8.0+ (or MariaDB 10.5+)
- A terminal / MySQL Workbench / any MySQL client

### Step 1 — Create schema and load sample data
```bash
mysql -u root -p < schema.sql
```
This creates the `community_portal` database, all tables, and inserts sample data.

### Step 2 — Run the exercise queries
```bash
mysql -u root -p community_portal < queries.sql
```

### Or run interactively
```sql
SOURCE /full/path/to/schema.sql;
SOURCE /full/path/to/queries.sql;
```

### Verify setup
After running `schema.sql` you should see:
```
+---------------+-----------+
| table_name    | row_count |
+---------------+-----------+
| Users         |         5 |
| Events        |         3 |
| Sessions      |         4 |
| Registrations |         5 |
| Feedback      |         3 |
| Resources     |         3 |
+---------------+-----------+
```

---

## Schema Overview

### Entity-Relationship Diagram (ASCII)

```
┌────────────┐       ┌─────────────────────────────────────────┐
│   Users    │       │                  Events                  │
├────────────┤       ├─────────────────────────────────────────┤
│ user_id PK │──┐    │ event_id    PK                          │
│ full_name  │  │    │ title                                    │
│ email      │  │    │ description                              │
│ city       │  │    │ city                                     │
│ reg_date   │  │    │ start_date                               │
└────────────┘  │    │ end_date                                 │
                │    │ status  (upcoming/completed/cancelled)   │
                └───►│ organizer_id  FK → Users                │
                     └─────────────────┬───────────────────────┘
                                       │ event_id (FK)
              ┌────────────────────────┼────────────────────────┐
              │                        │                        │
              ▼                        ▼                        ▼
   ┌──────────────────┐   ┌──────────────────────┐   ┌─────────────────┐
   │    Sessions      │   │    Registrations     │   │   Resources     │
   ├──────────────────┤   ├──────────────────────┤   ├─────────────────┤
   │ session_id    PK │   │ registration_id   PK │   │ resource_id  PK │
   │ event_id      FK │   │ user_id           FK │   │ event_id     FK │
   │ title            │   │ event_id          FK │   │ resource_type   │
   │ speaker_name     │   │ registration_date    │   │ resource_url    │
   │ start_time       │   └──────────────────────┘   │ uploaded_at     │
   │ end_time         │                               └─────────────────┘
   └──────────────────┘
                                       │
                                       ▼
                          ┌──────────────────────┐
                          │       Feedback       │
                          ├──────────────────────┤
                          │ feedback_id       PK │
                          │ user_id           FK │
                          │ event_id          FK │
                          │ rating (1–5)         │
                          │ comments             │
                          │ feedback_date        │
                          └──────────────────────┘
```

### Tables Summary

| Table | Rows (sample) | Purpose |
|-------|--------------|---------|
| `Users` | 5 | Portal members — name, email, city, join date |
| `Events` | 3 | Community events with status lifecycle |
| `Sessions` | 4 | Talk/workshop sessions within an event |
| `Registrations` | 5 | Which user registered for which event |
| `Feedback` | 3 | Star ratings + comments from attendees |
| `Resources` | 3 | PDFs, images, links attached to events |

---

## Sample Data

### Users
| user_id | full_name | city |
|---------|-----------|------|
| 1 | Alice Johnson | New York |
| 2 | Bob Smith | Los Angeles |
| 3 | Charlie Lee | Chicago |
| 4 | Diana King | New York |
| 5 | Ethan Hunt | Los Angeles |

### Events
| event_id | title | city | status |
|----------|-------|------|--------|
| 1 | Tech Innovators Meetup | New York | upcoming |
| 2 | AI & ML Conference | Chicago | completed |
| 3 | Frontend Development Bootcamp | Los Angeles | upcoming |

---

## Exercise Reference — All 25 Queries

| # | Title | Key Technique |
|---|-------|---------------|
| 1 | User Upcoming Events | JOIN 3 tables, filter by city & status |
| 2 | Top Rated Events | GROUP BY + HAVING COUNT ≥ 10 |
| 3 | Inactive Users | LEFT JOIN + HAVING on MAX date |
| 4 | Peak Session Hours | TIME() function + GROUP BY |
| 5 | Most Active Cities | COUNT(DISTINCT) + GROUP BY city |
| 6 | Event Resource Summary | Conditional aggregation SUM(CASE WHEN) |
| 7 | Low Feedback Alerts | JOIN 3 tables, filter rating < 3 |
| 8 | Sessions per Upcoming Event | JOIN + WHERE status + GROUP BY |
| 9 | Organizer Event Summary | GROUP BY organizer + status |
| 10 | Feedback Gap | LEFT JOIN Feedback, HAVING COUNT = 0 |
| 11 | Daily New User Count | WHERE date range + GROUP BY date |
| 12 | Event with Maximum Sessions | Subquery for MAX count (handles ties) |
| 13 | Average Rating per City | JOIN Feedback + Events, GROUP BY city |
| 14 | Most Registered Events | GROUP BY + ORDER BY + LIMIT 3 |
| 15 | Session Time Conflict | Self-JOIN with overlap condition |
| 16 | Unregistered Active Users | LEFT JOIN + IS NULL check |
| 17 | Multi-Session Speakers | GROUP BY speaker, HAVING COUNT > 1 |
| 18 | Resource Availability Check | LEFT JOIN Resources, WHERE IS NULL |
| 19 | Completed Events Summary | Double LEFT JOIN + WHERE status |
| 20 | User Engagement Index | LEFT JOIN two tables, computed column |
| 21 | Top Feedback Providers | GROUP BY user, ORDER + LIMIT 5 |
| 22 | Duplicate Registrations | GROUP BY user+event, HAVING COUNT > 1 |
| 23 | Registration Trends | DATE_FORMAT + GROUP BY month |
| 24 | Average Session Duration | TIMESTAMPDIFF(MINUTE) + AVG |
| 25 | Events Without Sessions | LEFT JOIN Sessions, WHERE IS NULL |

---

## Key SQL Concepts Covered

| Concept | Exercises |
|---------|-----------|
| INNER JOIN | 1, 5, 7, 8, 9, 13, 14, 17, 21, 24 |
| LEFT JOIN | 3, 6, 10, 16, 18, 19, 20, 25 |
| Self-JOIN | 15 |
| GROUP BY + HAVING | 2, 3, 4, 8, 9, 10, 12, 17, 22 |
| Subqueries | 12 |
| Conditional Aggregation | 6 |
| COUNT(DISTINCT …) | 5, 19, 20 |
| Date Functions (CURDATE, INTERVAL, DATE_FORMAT) | 3, 11, 16, 23 |
| TIMESTAMPDIFF | 24 |
| TIME() extraction | 4 |
| LIMIT | 5, 14, 21 |
| ENUM filtering | 1, 8, 18, 19, 25 |

---

## File Structure

```
Module_2/
├── schema.sql     ← CREATE TABLE + INSERT sample data + row-count verification
├── queries.sql    ← All 25 exercise queries, clearly labeled
└── README.md      ← This file
```

---

## Notes

> **Exercise 2 note:** The sample dataset has only 3 feedback rows (fewer than the `HAVING COUNT >= 10` threshold), so Exercise 2 returns an empty result set by design. The query itself is correct — add more rows to see results.

> **Exercise 11 and 16 note:** The sample users registered in late 2024 / early 2025. Depending on when you run these queries, the date-range filters (`INTERVAL 7 DAY`, `INTERVAL 30 DAY`) may return empty sets. This is expected — the queries are correct for live production data.

> **MySQL 8.0+ recommended** for full compatibility with `GROUP_CONCAT`, `TIMESTAMPDIFF`, `DATE_FORMAT`, `TIME()`, and window functions.
