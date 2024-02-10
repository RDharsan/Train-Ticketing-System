$(document).ready(function () {
    // Function to load travellers and display them in the list
    function loadTravellers() {
        $.get("/api/Traveller", function (data) {
            $("#travellerList").empty();
            data.forEach(function (traveller) {
                $("#travellerList").append(`<li>${traveller.name} - ${traveller.address} - ${traveller.phone} <button class="btn btn-warning edit" data-nic="${traveller.nic}">Edit</button></li>`);
            });
        });
    }

    // Load travellers on page load
    loadTravellers();

    // Submit form to add/edit a traveller
    $("#travellerForm").submit(function (e) {
        e.preventDefault();
        const travellerNIC = $("#travellerNIC").val();
        const travellerName = $("#travellerName").val();
        const travellerAddress = $("#travellerAddress").val();
        const travellerPhone = $("#travellerPhone").val();
        const apiUrl = travellerNIC ? `/api/Traveller/${travellerNIC}` : "/api/Traveller";

        $.ajax({
            url: apiUrl,
            type: travellerNIC ? "PUT" : "POST",
            data: JSON.stringify({ name: travellerName, address: travellerAddress, phone: travellerPhone }),
            contentType: "application/json",
            success: function (result) {
                alert(result);
                loadTravellers();
                $("#travellerNIC").val("");
                $("#travellerName").val("");
                $("#travellerAddress").val("");
                $("#travellerPhone").val("");
            }
        });
    });

    // Edit button click event
    $("#travellerList").on("click", ".edit", function () {
        const travellerNIC = $(this).data("nic");
        $.get(`/api/Traveller/${travellerNIC}`, function (traveller) {
            $("#travellerNIC").val(traveller.nic);
            $("#travellerName").val(traveller.name);
            $("#travellerAddress").val(traveller.address);
            $("#travellerPhone").val(traveller.phone);
        });
    });

    // Submit form to delete a traveller
    $("#deleteTravellerForm").submit(function (e) {
        e.preventDefault();
        const travellerNICToDelete = $("#deleteTravellerNIC").val();
        $.ajax({
            url: `/api/Traveller/${travellerNICToDelete}`,
            type: "DELETE",
            success: function (result) {
                alert(result);
                loadTravellers();
                $("#deleteTravellerNIC").val("");
            }
        });
    });
});
