/**
 * =============================================================
 * COMMUNITY EVENT PORTAL — main.js
 * =============================================================
 *
 * To debug: Open Chrome DevTools > Console tab.
 *   Set breakpoints in Sources tab.
 *   Use console.table(eventsArray) to inspect all events.
 *   Use the Network tab to watch fetch() calls.
 *   Use Application > Local Storage to inspect saved prefs.
 *
 * React/Vue would replace jQuery below with component-based
 * state management (useState, reactive(), etc.)
 * =============================================================
 */

'use strict';

// =============================================================
// 1. BASICS & SETUP
// =============================================================

console.log('Welcome to the Community Portal');

// window.onload fires after ALL resources (images, scripts) load
window.onload = function () {
  alert('Page loaded!'); // required for exercise; remove in production
  initPortal();
};

// =============================================================
// 2. DATA TYPES & OPERATORS
// =============================================================

const eventName     = 'City Music Night'; // const — string
const eventDate     = '2025-07-15';       // const — string
let   availableSeats = 50;               // let   — number (mutable)

// Template literal
console.log(`${eventName} on ${eventDate} — ${availableSeats} seats left`);

// ++ and -- used in handleRegister / handleCancel below

// =============================================================
// 5. OBJECTS & PROTOTYPES  (defined before arrays that use it)
// =============================================================

class CommunityEvent {
  constructor(id, name, date, category, location, seats, fee) {
    this.id       = id;
    this.name     = name;
    this.date     = date;
    this.category = category;
    this.location = location;
    this.seats    = seats;
    this.fee      = fee;
  }

  // Prototype method — returns true/false
  checkAvailability() {
    return this.seats > 0;
  }
}

// =============================================================
// 6. ARRAYS & METHODS
// =============================================================

const eventsArray = [
  new CommunityEvent(1, 'City Music Night',  '2025-07-15', 'Music',     'Central Park',    50,  0),
  new CommunityEvent(2, 'Baking Workshop',   '2025-07-20', 'Workshop',  'Community Hall',  20,  100),
  new CommunityEvent(3, 'Yoga in the Park',  '2025-08-01', 'Health',    'Riverside Garden', 30, 50),
  new CommunityEvent(4, 'Photography Walk',  '2025-08-10', 'Workshop',  'Old Town',         15, 200),
  new CommunityEvent(5, 'Jazz Evening',      '2025-08-22', 'Music',     'Town Square',       0,  0),
  new CommunityEvent(6, 'Kids Coding Camp',  '2025-09-05', 'Education', 'Library',          25, 150),
];

// --- Array method demos (run once at load) ---

// .filter() — only Music events
const musicEvents = eventsArray.filter(e => e.category === 'Music');
console.log('[Array.filter] Music events:', musicEvents.map(e => e.name));

// .map() — format card titles
const formattedTitles = eventsArray.map(e => `${e.category}: ${e.name}`);
console.log('[Array.map] Formatted titles:', formattedTitles);

// .reduce() — total available seats across all events
const totalAvailable = eventsArray.reduce((sum, e) => sum + e.seats, 0);
console.log('[Array.reduce] Total available seats:', totalAvailable);

// Object.entries() — log all properties of first event
console.log('=== CommunityEvent properties (Object.entries) ===');
Object.entries(eventsArray[0]).forEach(([key, val]) => {
  console.log(`  ${key}: ${val}`);
});

// =============================================================
// 4. FUNCTIONS, SCOPE, CLOSURES, HIGHER-ORDER FUNCTIONS
// =============================================================

/**
 * addEvent — adds a new event to eventsArray (.push)
 * @param {CommunityEvent} event
 */
const addEvent = (event) => {
  eventsArray.push(event); // .push()
  console.log('[addEvent] Added:', event.name, '| Total events:', eventsArray.length);
};

/**
 * registerUser — reduces seat count; default param userName = "Guest"
 * Wraps in try-catch (Req #3)
 * @param {number} eventId
 * @param {string} userName
 * @returns {{ success: boolean, event?: CommunityEvent, error?: string }}
 */
const registerUser = (eventId, userName = 'Guest') => { // default parameter
  try {
    const event = eventsArray.find(e => e.id === eventId); // .find()
    if (!event) throw new Error(`Event ID ${eventId} not found.`);
    if (!event.checkAvailability()) throw new Error(`No seats left for "${event.name}".`);

    event.seats--; // -- operator
    availableSeats--; // -- on the module-level counter

    console.log(`[registerUser] "${userName}" registered for "${event.name}". Seats left: ${event.seats}`);
    return { success: true, event };
  } catch (err) {
    console.error('[registerUser] Error:', err.message); // console.error (Req #3)
    return { success: false, error: err.message };
  }
};

/**
 * filterEventsByCategory — returns filtered array
 * @param {string} category
 * @returns {CommunityEvent[]}
 */
const filterEventsByCategory = (category) =>
  eventsArray.filter(e => e.category === category);

/**
 * Closure — createCategoryCounter
 * Returns a function that tracks total registrations for a category
 * @param {string} category
 * @returns {Function}
 */
const createCategoryCounter = (category) => {
  let count = 0; // closed-over variable
  return () => {
    count++;
    console.log(`[Closure] ${category} registrations this session: ${count}`);
    return count;
  };
};

// Build one counter per category
const categoryCounters = {};
['Music', 'Workshop', 'Health', 'Education'].forEach(cat => {
  categoryCounters[cat] = createCategoryCounter(cat);
});

/**
 * Higher-order function — searchEvents
 * Accepts a callback for dynamic filtering strategy
 * @param {string} query
 * @param {Function} callback  receives filtered results
 * @returns {any}
 */
const searchEvents = (query, callback) => {
  const results = eventsArray.filter(e =>
    e.name.toLowerCase().includes(query.toLowerCase())
  );
  return callback(results); // pass callback
};

// Helper — find event by ID using .find()
const findEventById = (id) => eventsArray.find(e => e.id === id);

// =============================================================
// 7. DOM MANIPULATION — Render Cards
// =============================================================

/**
 * renderEventCards — creates and appends card elements
 * Uses createElement, appendChild, querySelectorAll
 * @param {CommunityEvent[]} events
 */
const renderEventCards = (events) => {
  const container = document.querySelector('#eventsContainer'); // querySelector
  if (!container) return;
  container.innerHTML = '';

  const today = new Date();

  if (events.length === 0) {
    const msg = document.createElement('p');
    msg.className = 'no-results';
    msg.textContent = 'No events found matching your criteria.';
    container.appendChild(msg);
    return;
  }

  events.forEach(event => {
    // 10. Destructuring
    const { name, date, category, seats, fee } = event;
    // 10. Optional chaining + nullish coalescing
    const location = event?.location ?? 'TBD';

    const eventDateObj   = new Date(date);
    const isPast         = eventDateObj < today;
    const noSeats        = seats === 0;
    const formattedFee   = fee === 0 ? 'Free' : `$${fee}`;
    const formattedDate  = eventDateObj.toLocaleDateString('en-US', {
      year: 'numeric', month: 'short', day: 'numeric'
    });

    // 3. if-else — skip / flag past events or zero-seat events
    if (isPast) {
      console.log(`[renderEventCards] Past event flagged: ${name}`);
    } else if (noSeats) {
      console.log(`[renderEventCards] Sold-out event flagged: ${name}`);
    }

    // createElement + appendChild
    const card = document.createElement('div');
    card.className    = 'eventCard js-event-card';
    card.dataset.id   = event.id;
    card.dataset.category = category;

    card.innerHTML = `
      <div class="card-top">
        <span class="tag">${category}</span>
        ${isPast  ? '<span class="badge-status badge-past">Past</span>'   : ''}
        ${noSeats && !isPast ? '<span class="badge-status badge-full">Full</span>' : ''}
      </div>
      <h3>${name}</h3>
      <p class="card-detail">&#128197; ${formattedDate}</p>
      <p class="card-detail">&#128205; ${location}</p>
      <p class="card-detail"><strong class="fee-tag">Fee: ${formattedFee}</strong></p>
      <p class="seats-display" id="seats-${event.id}">
        &#128194; <span class="seats-count">${seats}</span> seats left
      </p>
      <div class="card-actions">
        <button
          class="btn btn-primary btn-sm btn-register"
          data-event-id="${event.id}"
          data-event-name="${name}"
          ${noSeats || isPast ? 'disabled' : ''}
        >${noSeats ? 'Sold Out' : isPast ? 'Ended' : 'Register'}</button>
        <button
          class="btn btn-outline btn-sm btn-cancel"
          data-event-id="${event.id}"
          style="display:none"
        >Cancel</button>
      </div>
    `;

    container.appendChild(card); // appendChild
  });

  // 14. jQuery — fadeIn when cards are rendered
  if (typeof $ !== 'undefined') {
    $('.js-event-card').hide().fadeIn(400);
  }

  attachCardListeners();
  refreshStatsBar();
};

// =============================================================
// 8. EVENT HANDLING — Card Buttons
// =============================================================

/** Attach register/cancel listeners to all rendered cards */
const attachCardListeners = () => {
  // onclick — register
  document.querySelectorAll('.btn-register').forEach(btn => { // querySelectorAll
    btn.addEventListener('click', function () {
      const eventId   = parseInt(this.dataset.eventId, 10);
      const eventName = this.dataset.eventName;
      handleRegister(eventId, eventName, this);
    });
  });

  // onclick — cancel
  document.querySelectorAll('.btn-cancel').forEach(btn => {
    btn.addEventListener('click', function () {
      handleCancel(parseInt(this.dataset.eventId, 10), this);
    });
  });
};

/** Register: reduces seat, updates UI */
const handleRegister = (eventId, name, btn) => {
  const result = registerUser(eventId); // default userName = "Guest"
  if (!result.success) {
    showToast(`❌ ${result.error}`, 'error');
    return;
  }

  // Update seat count in UI
  const seatsCount = document.querySelector(`#seats-${eventId} .seats-count`);
  if (seatsCount) seatsCount.textContent = result.event.seats;

  // Disable button if seats hit 0
  if (result.event.seats === 0) {
    btn.disabled     = true;
    btn.textContent  = 'Sold Out';
  }

  // Show cancel button
  const cancelBtn = btn.closest('.card-actions').querySelector('.btn-cancel');
  if (cancelBtn) cancelBtn.style.display = 'inline-flex';

  // Closure counter
  const event = findEventById(eventId);
  if (event && categoryCounters[event.category]) {
    const count = categoryCounters[event.category]();
    const countEl = document.querySelector('#categoryCount');
    if (countEl) countEl.textContent = `${event.category} registrations this session: ${count}`;
  }

  availableSeats--; // -- operator
  showToast(`✅ Registered for "${name}"!`, 'success');
  refreshStatsBar();
  console.log('[handleRegister] Done. eventId:', eventId);
};

/** Cancel: restores seat, re-enables register button */
const handleCancel = (eventId, cancelBtn) => {
  const event = findEventById(eventId);
  if (!event) return;

  event.seats++;    // ++ operator
  availableSeats++; // ++ on module counter

  const seatsCount = document.querySelector(`#seats-${eventId} .seats-count`);
  if (seatsCount) seatsCount.textContent = event.seats;

  // Re-enable register button
  const card        = cancelBtn.closest('.js-event-card');
  const registerBtn = card.querySelector('.btn-register');
  if (registerBtn) {
    registerBtn.disabled    = false;
    registerBtn.textContent = 'Register';
  }
  cancelBtn.style.display = 'none';

  showToast(`↩ Cancelled registration for "${event.name}".`, 'info');
  refreshStatsBar();
  console.log('[handleCancel] Cancelled. eventId:', eventId);
};

// =============================================================
// 8. EVENT HANDLING — Filters & Search
// =============================================================

let currentCategory = 'All';
let currentSearch   = '';

/** Re-run filters and re-render */
const applyFilters = () => {
  // 10. Spread — clone before filtering (don't mutate original)
  let cloned = [...eventsArray];

  if (currentCategory !== 'All') {
    cloned = cloned.filter(e => e.category === currentCategory);
  }

  if (currentSearch.trim()) {
    // Higher-order: pass identity callback, then apply category on top
    cloned = searchEvents(currentSearch, results => results);
    if (currentCategory !== 'All') {
      cloned = cloned.filter(e => e.category === currentCategory);
    }
  }

  console.log(
    `[applyFilters] Category: "${currentCategory}" | Search: "${currentSearch}" | Matches: ${cloned.length}`
  );

  // 14. jQuery — fadeOut existing cards, then re-render
  if (typeof $ !== 'undefined') {
    $('.js-event-card').fadeOut(200, () => renderEventCards(cloned));
  } else {
    renderEventCards(cloned);
  }
};

/** Wire up filter bar controls */
const initFilters = () => {
  // onchange — category dropdown
  const catFilter = document.querySelector('#categoryFilter');
  if (catFilter) {
    catFilter.addEventListener('change', function () {
      currentCategory = this.value;
      console.log('[Filter] Category →', currentCategory);
      applyFilters();
    });
  }

  // input + keydown — search box (live filter)
  const searchBox = document.querySelector('#searchBox');
  if (searchBox) {
    searchBox.addEventListener('input', function () {
      currentSearch = this.value;
      console.log('[Search] Query →', currentSearch);
      applyFilters();
    });
    searchBox.addEventListener('keydown', function (e) {
      if (e.key === 'Escape') {
        this.value  = '';
        currentSearch = '';
        applyFilters();
      }
    });
  }

  // Reset Filters button
  const resetBtn = document.querySelector('#resetFiltersBtn');
  if (resetBtn) {
    resetBtn.addEventListener('click', () => {
      currentCategory = 'All';
      currentSearch   = '';
      const cf = document.querySelector('#categoryFilter');
      const sb = document.querySelector('#searchBox');
      if (cf) cf.value = 'All';
      if (sb) sb.value = '';
      console.log('[Reset] Filters cleared. Re-rendering all events.');
      renderEventCards([...eventsArray]);
    });
  }
};

// =============================================================
// 9. ASYNC JS — Loader helpers
// =============================================================

const showLoader = () => {
  const el = document.querySelector('#loader');
  if (el) el.style.display = 'flex';
};

const hideLoader = () => {
  const el = document.querySelector('#loader');
  if (el) el.style.display = 'none';
};

/**
 * VERSION 1 — fetch().then().catch()
 * Loads db.json and renders cards using Promise chain
 */
const loadEventsWithPromise = () => {
  showLoader();
  console.log('[Fetch/Promise] Requesting db.json…');

  fetch('db.json')
    .then(response => {
      if (!response.ok) throw new Error(`HTTP ${response.status}`);
      return response.json();
    })
    .then(data => {
      console.log('[Fetch/Promise] Loaded:', data);
      hideLoader();
      // Sync seats from JSON into eventsArray (in case db differs)
      data.forEach(d => {
        const existing = eventsArray.find(e => e.id === d.id);
        if (existing) existing.seats = d.seats;
      });
      renderEventCards([...eventsArray]);
    })
    .catch(err => {
      // Graceful fallback — use inline eventsArray
      console.warn('[Fetch/Promise] db.json unavailable. Using inline data. Error:', err.message);
      hideLoader();
      renderEventCards([...eventsArray]);
    });
};

/**
 * VERSION 2 — async / await (same logic, different syntax)
 * Toggle USE_ASYNC flag below to switch versions
 */
const loadEventsAsync = async () => {
  showLoader();
  console.log('[Async/Await] Requesting db.json…');
  try {
    const response = await fetch('db.json');
    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    const data = await response.json();
    console.log('[Async/Await] Loaded:', data);
    hideLoader();
    data.forEach(d => {
      const existing = eventsArray.find(e => e.id === d.id);
      if (existing) existing.seats = d.seats;
    });
    renderEventCards([...eventsArray]);
  } catch (err) {
    console.warn('[Async/Await] db.json unavailable. Falling back. Error:', err.message);
    hideLoader();
    renderEventCards([...eventsArray]);
  }
};

// Toggle this flag to switch between the two versions (Req #9)
const USE_ASYNC_AWAIT = true;

// =============================================================
// 11. FORMS — Validation & Submission
// =============================================================

/** Show error text below a form field */
const showFieldError = (id, msg) => {
  const el = document.querySelector(`#${id}`);
  if (el) el.textContent = msg;
};

/** Wire up form validation and submission */
const initFormValidation = () => {
  const form = document.querySelector('#regForm');
  if (!form) return;

  form.addEventListener('submit', async function (e) {
    e.preventDefault(); // prevent default submission
    console.log('[Form] Submit event fired.');

    // Clear previous errors
    document.querySelectorAll('.field-error').forEach(el => (el.textContent = ''));

    // Capture values via form.elements
    const nameEl  = form.elements['fullName'];
    const emailEl = form.elements['emailAddr'];
    const typeEl  = form.elements['eventType'];

    let valid = true;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // Inline validation
    if (!nameEl?.value.trim()) {
      showFieldError('nameError', 'Full name is required.');
      valid = false;
    }
    if (!emailEl?.value.trim()) {
      showFieldError('emailError', 'Email address is required.');
      valid = false;
    } else if (!emailRegex.test(emailEl.value)) {
      showFieldError('emailError', 'Please enter a valid email address.');
      valid = false;
    }
    if (!typeEl?.value) {
      showFieldError('eventTypeError', 'Please select an event type.');
      valid = false;
    }

    if (!valid) {
      console.log('[Form] Validation failed — aborting submission.');
      return;
    }

    const formData = {
      name:      nameEl.value.trim(),
      email:     emailEl.value.trim(),
      eventType: typeEl.value,
    };
    console.log('[Form] Validation passed:', formData);

    // Also update the <output> element (HTML5 exercise requirement)
    const outputEl = document.querySelector('#formOutput');
    if (outputEl) {
      outputEl.textContent = `✅ Thanks, ${formData.name}! You're registered for "${typeEl.options[typeEl.selectedIndex].text}". Confirmation sent to ${formData.email}.`;
      outputEl.classList.add('show');
      outputEl.style.display = 'block';
    }

    // 12. POST to mock API
    await submitRegistration(formData);
  });
};

// =============================================================
// 12. AJAX & FETCH API — POST registration
// =============================================================

/**
 * POST form data to JSONPlaceholder as mock API
 * Shows "Registering..." → then success / error after 1.5s delay
 * @param {{ name: string, email: string, eventType: string }} formData
 */
const submitRegistration = async (formData) => {
  const msgEl = document.querySelector('#registrationMessage');

  const setMsg = (text, cls) => {
    if (!msgEl) return;
    msgEl.textContent  = text;
    msgEl.className    = `reg-message ${cls}`;
    msgEl.style.display = 'block';
  };

  setMsg('Registering…', 'reg-info');
  console.log('[AJAX] POSTing registration:', formData);

  // 1.5s simulated delay (setTimeout wrapped in Promise)
  await new Promise(resolve => setTimeout(resolve, 1500));

  try {
    const response = await fetch('https://jsonplaceholder.typicode.com/posts', {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify({
        title:  `Registration: ${formData.eventType}`,
        body:   `${formData.name} (${formData.email}) registered`,
        userId: 1,
      }),
    });

    if (!response.ok) throw new Error(`Server responded ${response.status}`);

    const result = await response.json();
    console.log('[AJAX] Response:', result);

    setMsg('✅ Registration successful!', 'reg-success');
    document.querySelector('#regForm')?.reset();
    document.querySelectorAll('.field-error').forEach(el => (el.textContent = ''));

  } catch (err) {
    console.error('[AJAX] POST failed:', err.message);
    setMsg('❌ Something went wrong. Try again.', 'reg-error');
  }
};

// =============================================================
// 13. DEBUGGING — Intentional broken fetch
// =============================================================
const debugBrokenFetch = () => {
  console.log('[Debug] Testing broken fetch — error should be caught below:');
  fetch('https://this-url-definitely-does-not-exist-12345.example/api')
    .then(r => r.json())
    .catch(err => {
      console.error('[Debug] Intentional broken-URL fetch error caught ✓:', err.message);
    });
};

// =============================================================
// 14. JQUERY — Setup
// React/Vue would replace this jQuery with component-based
// state management (useState / reactive())
// =============================================================

const initJQuery = () => {
  if (typeof $ === 'undefined') {
    console.warn('[jQuery] Not loaded — skipping jQuery setup.');
    return;
  }
  console.log('[jQuery] Version', $.fn.jquery, 'ready.');

  // $('#registerBtn') click handler (targets the form submit button)
  $('#submitBtn').click(function () {
    console.log('[jQuery] #submitBtn clicked (jQuery handler).');
  });

  // Delegate register clicks — cards may not exist yet
  $(document).on('click', '.btn-register:not(:disabled)', function () {
    $(this).addClass('btn-pulse');
    setTimeout(() => $(this).removeClass('btn-pulse'), 300);
  });

  // Fade-in effect on nav links for polish
  $('nav a').each(function (i) {
    $(this).delay(i * 50).fadeIn(200);
  });
};

// =============================================================
// UTILITY HELPERS
// =============================================================

/** Show a toast / temp message above the events container */
const showToast = (msg, type = 'success') => {
  let toastEl = document.querySelector('#toastMessage');
  if (!toastEl) {
    toastEl           = document.createElement('div');
    toastEl.id        = 'toastMessage';
    const container   = document.querySelector('#eventsContainer');
    if (container) container.before(toastEl);
  }
  toastEl.textContent  = msg;
  toastEl.className    = `toast-msg toast-${type}`;
  toastEl.style.display = 'block';

  // jQuery fadeOut if available, else plain timeout
  if (typeof $ !== 'undefined') {
    $(toastEl).stop(true).show().delay(2800).fadeOut(400);
  } else {
    clearTimeout(toastEl._timer);
    toastEl._timer = setTimeout(() => (toastEl.style.display = 'none'), 3000);
  }
};

/** Refresh the stats bar (total available seats) */
const refreshStatsBar = () => {
  const total = eventsArray.reduce((sum, e) => sum + e.seats, 0); // .reduce()
  const el    = document.querySelector('#totalSeatsDisplay');
  if (el) el.textContent = `Total available seats across all events: ${total}`;
};

// =============================================================
// INIT — ties everything together
// =============================================================

const initPortal = () => {
  console.log('[Init] Starting portal initialisation…');
  console.table(eventsArray); // console.table — full event list

  initFilters();
  initFormValidation();
  initJQuery();

  // Demonstrate addEvent with .push()
  const demoEvent = new CommunityEvent(7, 'Art in the Park', '2025-10-12', 'Workshop', 'Sculpture Garden', 18, 75);
  addEvent(demoEvent);
  console.log('[Demo] eventsArray after addEvent push:', eventsArray.map(e => e.name));

  // Remove demo event so it doesn't appear in UI (exercise demo only)
  eventsArray.pop();

  // Demonstrate filterEventsByCategory
  const workshops = filterEventsByCategory('Workshop');
  console.log('[Demo] Workshop events:', workshops.map(e => e.name));

  // Demonstrate searchEvents HOF
  const searchResult = searchEvents('yoga', results => results);
  console.log('[Demo] searchEvents("yoga"):', searchResult.map(e => e.name));

  // 13. Debug — broken fetch (error is caught and logged)
  debugBrokenFetch();

  // 9. Load events — switch versions via USE_ASYNC_AWAIT flag
  if (USE_ASYNC_AWAIT) {
    loadEventsAsync();   // Version 2: async/await
  } else {
    loadEventsWithPromise(); // Version 1: .then().catch()
  }

  console.log('[Init] Portal initialised. USE_ASYNC_AWAIT =', USE_ASYNC_AWAIT);
};
