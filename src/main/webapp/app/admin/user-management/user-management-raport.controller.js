(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('UserManagementRaportController', UserManagementRaportController);

    UserManagementRaportController.$inject = ['$stateParams', 'User'];

    function UserManagementRaportController($stateParams, User,calendarConfig,moment, alert) {
        var vm = this;


        Array.prototype.sum = function (prop) {
            var total = 0
            for ( var i = 0, _len = this.length; i < _len; i++ ) {
                total += this[i][prop]
            }
            return total
        }



        vm.load = load;
        vm.user = {};



        vm.exampleStartDate = new Date();
        vm.exampleStartDate.setHours(12,0);
        vm.exampleEndDate = new Date();
        vm.exampleEndDate.setHours(15,0);


        vm.calendarView = 'month';
        vm.viewDate = new Date()

        //Static data, needs endpoint
        vm.events = [
             {
               title: 'Worked 2 hours', // The title of the event
               startsAt: vm.exampleStartDate, // A javascript date object for when the event starts
               endsAt: vm.exampleEndDate, // Optional - a javascript date object for when the event ends
               hours:2,
               color: { // can also be calendarConfig.colorTypes.warning for shortcuts to the deprecated event types
                 primary: '#e3bc08', // the primary event color (should be darker than secondary)
                 secondary: '#fdf1ba' // the secondary event color (should be lighter than primary)
               },
               actions: [],
               draggable: false, //Allow an event to be dragged and dropped
               resizable: false, //Allow an event to be resizable
               incrementsBadgeTotal: true, //If set to false then will not count towards the badge total amount on the month and year view
               recursOn: 'year', // If set the event will recur on the given period. Valid values are year or month
               cssClass: 'a-css-class-name', //A CSS class (or more, just separate with spaces) that will be added to the event when it is displayed on each view. Useful for marking an event as selected / active etc
               allDay: true // set to true to display the event as an all day event on the day view
             }
           ];

        vm.load($stateParams.login);

        function load(login) {
            User.get({login: login}, function(result) {
                vm.user = result;
            });
        }
    }
})();
