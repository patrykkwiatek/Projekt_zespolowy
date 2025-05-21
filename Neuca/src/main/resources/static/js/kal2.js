
const isLeapYear = (year) => {
    return (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
};

const getFebDays = (year) => {
    return isLeapYear(year) ? 29 : 28;
};

let currDate = new Date();
let curr_month = { value: currDate.getMonth() };
let curr_year = { value: currDate.getFullYear() };

let calendar = document.querySelector('.calendar');
const month_names = [
    'Styczeń', 'Luty', 'Marzec', 'Kwiecień',
    'Maj', 'Czerwiec', 'Lipiec', 'Sierpień',
    'Wrzesień', 'Październik', 'Listopad', 'Grudzień'
];

let month_picker = document.querySelector('#month-picker');
let month_list = calendar.querySelector('.month-list');

month_picker.onclick = () => {
    month_list.classList.add('show');
};

const generateCalendar = (month, year) => {
    let calendar_days = document.querySelector('.calendar-days');
    calendar_days.innerHTML = '';

    let calendar_header_year = document.querySelector('#year');

    let days_of_month = [
        31, getFebDays(year), 31, 30, 31, 30,
        31, 31, 30, 31, 30, 31
    ];

    let first_day = new Date(year, month, 1);
    let starting_day = (first_day.getDay() + 6) % 7;

    month_picker.innerHTML = month_names[month];
    calendar_header_year.innerHTML = year;

    for (let i = 0; i < days_of_month[month] + starting_day; i++) {
        let day = document.createElement('div');
        if (i >= starting_day) {
            day.classList.add('calendar-day-hover');
            day.innerHTML = i - starting_day + 1;
            day.innerHTML += `
                <span></span>
                <span></span>
                <span></span>
                <span></span>`;
            if (
                i - starting_day + 1 === currDate.getDate() &&
                year === currDate.getFullYear() &&
                month === currDate.getMonth()
            ) {
                day.classList.add('curr-date');
            }
        }
        calendar_days.appendChild(day);
    }
};

month_names.forEach((e, index) => {
    let month = document.createElement('div');
    month.innerHTML = `<div>${e}</div>`;
    month.onclick = () => {
        month_list.classList.remove('show');
        curr_month.value = index;
        generateCalendar(curr_month.value, curr_year.value);
    };
    month_list.appendChild(month);
});

document.querySelector('#prev-year').onclick = () => {
    --curr_year.value;
    generateCalendar(curr_month.value, curr_year.value);
};

document.querySelector('#next-change').onclick = () => {
    ++curr_year.value;
    generateCalendar(curr_month.value, curr_year.value);
};

generateCalendar(curr_month.value, curr_year.value);