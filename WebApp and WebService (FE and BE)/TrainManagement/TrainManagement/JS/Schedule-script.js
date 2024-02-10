$(document).ready(function () {
    // Function to load train schedules and display them in the list
    function loadSchedules() {
        $.get("/api/TrainSchedule", function (data) {
            $("#scheduleList").empty();
            data.forEach(function (schedule) {
                $("#scheduleList").append(`<li>${schedule.trainName} - ${schedule.trainDate} - ${schedule.trainSource} to ${schedule.trainDest} <button class="btn btn-warning edit" data-id="${schedule.trainScheduleId}">Edit</button></li>`);
            });
        });
    }

    // Load schedules on page load
    loadSchedules();

    // Submit form to add/edit a schedule
    $("#scheduleForm").submit(function (e) {
        e.preventDefault();
        const scheduleId = $("#scheduleId").val();
        const trainName = $("#trainName").val();
        const trainDate = $("#trainDate").val();
        const trainSource = $("#trainSource").val();
        const trainDest = $("#trainDest").val();
        const apiUrl = scheduleId ? `/api/TrainSchedule/${scheduleId}` : "/api/TrainSchedule";

        $.ajax({
            url: apiUrl,
            type: scheduleId ? "PUT" : "POST",
            data: JSON.stringify({ trainName, trainDate, trainSource, trainDest }),
            contentType: "application/json",
            success: function (result) {
                alert(result);
                loadSchedules();
                $("#scheduleId").val("");
                $("#trainName").val("");
                $("#trainDate").val("");
                $("#trainSource").val("");
                $("#trainDest").val("");
            }
        });
    });

    // Edit button click event
    $("#scheduleList").on("click", ".edit", function () {
        const scheduleId = $(this).data("id");
        $.get(`/api/TrainSchedule/${scheduleId}`, function (schedule) {
            $("#scheduleId").val(schedule.trainScheduleId);
            $("#trainName").val(schedule.trainName);
            $("#trainDate").val(schedule.trainDate);
            $("#trainSource").val(schedule.trainSource);
            $("#trainDest").val(schedule.trainDest);
        });
    });

    // Submit form to delete a schedule
    $("#deleteForm").submit(function (e) {
        e.preventDefault();
        const scheduleIdToDelete = $("#deleteScheduleId").val();
        $.ajax({
            url: `/api/TrainSchedule/${scheduleIdToDelete}`,
            type: "DELETE",
            success: function (result) {
                alert(result);
                loadSchedules();
                $("#deleteScheduleId").val("");
            }
        });
    });
});
