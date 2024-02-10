/*
 * Filename: Reservation.cs
 * Author: Dharsan.R
 * Date: September 30, 2023
 * Description: This file contains the definition of the Reservation class, which represents Reservation information.
 * It includes properties for Reservation  id, NIC, and TrainSchedule Id.
 */

using MongoDB.Bson;

namespace TrainManagement.Models
{
    // Reservation Class and its attributes
    public class Reservation
    {
        public ObjectId Id { get; set; }
        public int ReservationId { get; set; }
        public string NIC { get; set; }
        public int TrainScheduleId { get; set; }
    }
}
