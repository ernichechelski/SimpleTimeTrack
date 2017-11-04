(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('ProjectReportController', ProjectReportController);

    ProjectReportController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Project', 'User', 'Week', 'params'];

    function ProjectReportController($scope, $rootScope, $stateParams, previousState, entity, Project, User, Week, params) {

        var vm = this;
        
        vm.project = entity;
        vm.previousState = previousState.name;
        vm.test = params;
        vm.weeks = Week.query();
        vm.totalTime = 0;
        
        vm.actualUsers = {};
        vm.calculateTime = getUsersTime;
        vm.totalTime = 0;
        vm.start_week = $stateParams.start_week;
        vm.stop_week = $stateParams.stop_week;
      
        var checked_users = [];

        function getUsersTime(user){
            
            var result = [];
            if(typeof user != "undefined" && $.inArray(user.id, checked_users ) == -1 && vm.weeks.length > 0){
                
                checked_users.push(user.id);
                vm.actualUsers[user.id] = []
            $(vm.weeks).each(function(){

                if(this.number >= vm.start_week && this.number <= vm.stop_week && this.year == $stateParams.year &&
                    this.project.id == vm.project.id){
                    var s_week = this;
                        if(s_week.user.id == user.id ){
                            var timeInWeek = s_week.monday + s_week.tuesday + s_week.wednesday + s_week.thursday +s_week.friday +s_week.saturday +s_week.sunday;
                            var actual_user = {
                                id: user.id,
                                weekId: s_week.id,
                                week: s_week.number,
                                time: timeInWeek
                            }
                            result.push(actual_user);
                            vm.actualUsers[user.id].push(actual_user);
                            vm.totalTime += actual_user.time;
                        }
                }
            });
        }
        };
        
        var unsubscribe = $rootScope.$on('simpleTimeTrackApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
