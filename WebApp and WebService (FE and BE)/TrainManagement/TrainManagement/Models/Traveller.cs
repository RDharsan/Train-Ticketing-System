/*
 * Filename: Traveller.cs
 * Author: Dharsan.R
 * Date: September 30, 2023
 * Description: This file contains the definition of the Traveller class, which represents Traveller information.
 * It includes properties for traveller id, NIC, name, address, and phone number.
 */

using MongoDB.Bson;

namespace TrainManagement.Models
{
    // Traveller Class and its attributes
    public class Traveller
    {
        public ObjectId Id { get; set; }
        public string NIC { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string Phone { get; set; }
    }
}

