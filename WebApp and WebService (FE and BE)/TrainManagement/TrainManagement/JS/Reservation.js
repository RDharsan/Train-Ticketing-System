// Function to create a reservation
function createReservation() {
    const nic = $("#nicInput").val();
    const trainScheduleId = $("#trainScheduleIdInput").val();

    const newReservation = {
        NIC: nic,
        TrainScheduleId: trainScheduleId
    };

    $.ajax({
        type: "POST",
        url: "/api/Reservation",
        contentType: "application/json",
        data: JSON.stringify(newReservation),
        success: function (data) {
            alert("Reservation created successfully!");
            loadReservations(); // Refresh the reservation table
        },
        error: function (error) {
            alert("Error creating reservation: " + error.responseText);
        }
    });
}

// Function to load reservations into the table
function loadReservations() {
    $.ajax({
        type: "GET",
        url: "/api/Reservation",
        success: function (data) {
            const reservationTable = $("#reservationTable tbody");
            reservationTable.empty(); // Clear existing data

            data.forEach(function (reservation) {
                reservationTable.append(`
                    <tr>
                        <td>${reservation.ReservationId}</td>
                        <td>${reservation.NIC}</td>
                        <td>${reservation.Name}</td>
                        <td>${reservation.TrainScheduleId}</td>
                        <td>${reservation.TrainName}</td>
                        <td>${reservation.TrainSource}</td>
                        <td>${reservation.TrainDest}</td>
                        <td>${reservation.TrainDate}</td>
                        <td>
                            <button type="button" onclick="updateReservation(${reservation.ReservationId})">Update</button>
                            <button type="button" onclick="deleteReservation(${reservation.ReservationId})">Delete</button>
                        </td>
                    </tr>
                `);
            });
        },
        error: function (error) {
            alert("Error loading reservations: " + error.responseText);
        }
    });
}

// Function to update a reservation
function updateReservation(reservationId) {
    // Fetch the existing reservation details based on the reservationId
    $.ajax({
        type: "GET",
        url: `/api/Reservation/${reservationId}`,
        success: function (data) {
            // Assuming you have form elements for updating reservation details
            $("#updateNicInput").val(data.NIC);
            $("#updateTrainScheduleIdInput").val(data.TrainScheduleId);

            // Show a modal or form for updating reservation details
            $("#updateReservationModal").modal("show");

            // When the user submits the updated reservation details, call the update API
            $("#updateReservationButton").click(function () {
                const updatedNic = $("#updateNicInput").val();
                const updatedTrainScheduleId = $("#updateTrainScheduleIdInput").val();

                const updatedReservation = {
                    NIC: updatedNic,
                    TrainScheduleId: updatedTrainScheduleId
                };

                $.ajax({
                    type: "PUT",
                    url: `/api/Reservation/${reservationId}`,
                    contentType: "application/json",
                    data: JSON.stringify(updatedReservation),
                    success: function () {
                        alert("Reservation updated successfully!");
                        $("#updateReservationModal").modal("hide");
                        loadReservations(); // Refresh the reservation table
                    },
                    error: function (error) {
                        alert("Error updating reservation: " + error.responseText);
                    }
                });
            });
        },
        error: function (error) {
            alert("Error fetching reservation details: " + error.responseText);
        }
    });
}
// Function to delete a reservation
function deleteReservation(reservationId) {
    const confirmDelete = confirm("Are you sure you want to delete this reservation?");
    if (confirmDelete) {
        $.ajax({
            type: "DELETE",
            url: `/api/Reservation/${reservationId}`,
            success: function () {
                alert("Reservation deleted successfully!");
                loadReservations(); // Refresh the reservation table
            },
            error: function (error) {
                alert("Error deleting reservation: " + error.responseText);
            }
        });
    }
}

// Load reservations when the page loads
$(document).ready(function () {
    loadReservations();
});
