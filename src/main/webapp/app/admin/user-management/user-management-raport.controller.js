(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .config(['calendarConfig', function(calendarConfig) {
            calendarConfig.dateFormatter = 'moment'; // use moment to format dates
            moment.locale('en_gb', {
                week: {
                    dow: 1 // Monday is the first day of the week
                }
            });
        }])
        .controller('UserManagementRaportController', UserManagementRaportController);

    UserManagementRaportController.$inject = ['$stateParams', 'User'];

    function UserManagementRaportController($stateParams, User, calendarConfig, moment, alert) {
        var vm = this;

        Array.prototype.sum = function(prop) {
            var total = 0
            for (var i = 0, _len = this.length; i < _len; i++) {
                total += this[i][prop]
            }
            return total
        }

        vm.load = load;
        vm.user = {};
        vm.userWeeks = {};


        //Calendar config:
        vm.calendarView = 'month';
        vm.viewDate = new Date()


        //Static data, needs endpoint with provided model.
        vm.events = [];

        function dateFrom(week, year, dayOffset) {
            if (year == null) {
                year = (new Date()).getFullYear();
            }
            var date = firstWeekOfYear(year),
                weekTime = weeksToMilliseconds(week),
                targetTime = date.getTime() + weekTime;
            date.setTime(targetTime);
            date.setDate(date.getDate() + dayOffset);
            return date;

        }

        function weeksToMilliseconds(weeks) {
            return 1000 * 60 * 60 * 24 * 7 * (weeks - 1);
        }

        function firstWeekOfYear(year) {
            var date = new Date();
            date = firstDayOfYear(date, year);
            date = firstWeekday(date);
            return date;
        }

        function firstDayOfYear(date, year) {
            date.setYear(year);
            date.setDate(1);
            date.setMonth(0);
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            date.setMilliseconds(0);
            return date;
        }

        /**
         * Sets the given date as the first day of week of the first week of year.
         */
        function firstWeekday(firstOfJanuaryDate) {
            // 0 correspond au dimanche et 6 correspond au samedi.
            var FIRST_DAY_OF_WEEK = 1; // Monday, according to iso8601
            var WEEK_LENGTH = 7; // 7 days per week
            var day = firstOfJanuaryDate.getDay();
            day = (day === 0) ? 7 : day; // make the days monday-sunday equals to 1-7 instead of 0-6
            var dayOffset = -day + FIRST_DAY_OF_WEEK; // dayOffset will correct the date in order to get a Monday
            if (WEEK_LENGTH - day + 1 < 4) {
                // the current week has not the minimum 4 days required by iso 8601 => add one week
                dayOffset += WEEK_LENGTH;
            }
            return new Date(firstOfJanuaryDate.getTime() + dayOffset * 24 * 60 * 60 * 1000);
        }

        vm.load($stateParams.login);

        function load(login) {


            User.get({
                login: login
            }, function(result) {
                vm.user = result;
            });
            User.getWeeks({
                login: login
            }, function(result) {
                vm.userWeeks = result;
                console.log(vm.userWeeks);

                vm.userWeeks.forEach(function(entry) {

                    var monday = dateFrom(entry.number, entry.year, 0);
                    var tuesday = dateFrom(entry.number, entry.year, 1);
                    var wednesday = dateFrom(entry.number, entry.year, 2);
                    var thursday = dateFrom(entry.number, entry.year, 3);
                    var friday = dateFrom(entry.number, entry.year, 4);
                    var saturday = dateFrom(entry.number, entry.year, 5);
                    var sunday = dateFrom(entry.number, entry.year, 6);


                    vm.events.push({
                        startsAt: monday, // A javascript date object for when the event starts
                        hours: entry.monday,
                    });
                    vm.events.push({
                        startsAt: tuesday, // A javascript date object for when the event starts
                        hours: entry.tuesday,
                    });
                    vm.events.push({
                        startsAt: wednesday, // A javascript date object for when the event starts
                        hours: entry.wednesday,
                    });
                    vm.events.push({
                        startsAt: thursday, // A javascript date object for when the event starts
                        hours: entry.thursday,
                    });
                    vm.events.push({
                        startsAt: friday, // A javascript date object for when the event starts
                        hours: entry.friday,
                    });
                    vm.events.push({
                        startsAt: saturday, // A javascript date object for when the event starts
                        hours: entry.saturday,
                    });
                    vm.events.push({
                        startsAt: sunday, // A javascript date object for when the event starts
                        hours: entry.sunday,
                    });
                    console.log(vm.events);

                });
            });
        }
    }
})();
