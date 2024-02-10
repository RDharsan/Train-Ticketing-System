$(document).ready(function () {
    // Function to load trains and display them in the list
    function loadTrains() {
        $.get("/api/Train", function (data) {
            $("#trainList").empty();
            data.forEach(function (train) {
                $("#trainList").append(`<li>${train.trainName} - ${train.trainStatus} <button class="btn btn-warning edit" data-id="${train.trainId}">Edit</button></li>`);
            });
        });
    }

    // Load trains on page load
    loadTrains();

    // Submit form to add/edit a train
    $("#trainForm").submit(function (e) {
        e.preventDefault();
        const trainId = $("#trainId").val();
        const trainName = $("#trainName").val();
        const trainStatus = $("#trainStatus").val();
        const apiUrl = trainId ? `/api/Train/${trainId}` : "/api/Train";

        $.ajax({
            url: apiUrl,
            type: trainId ? "PUT" : "POST",
            data: JSON.stringify({ trainName, trainStatus }),
            contentType: "application/json",
            success: function (result) {
                alert(result);
                loadTrains();
                $("#trainId").val("");
                $("#trainName").val("");
                $("#trainStatus").val("");
            }
        });
    });

    // Edit button click event
    $("#trainList").on("click", ".edit", function () {
        const trainId = $(this).data("id");
        $.get(`/api/Train/${trainId}`, function (train) {
            $("#trainId").val(train.trainId);
            $("#trainName").val(train.trainName);
            $("#trainStatus").val(train.trainStatus);
        });
    });

    // Submit form to delete a train
    $("#deleteForm").submit(function (e) {
        e.preventDefault();
        const trainIdToDelete = $("#deleteTrainId").val();
        $.ajax({
            url: `/api/Train/${trainIdToDelete}`,
            type: "DELETE",
            success: function (result) {
                alert(result);
                loadTrains();
                $("#deleteTrainId").val("");
            }
        });
    });
});
