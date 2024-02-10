/*
 * Filename: Train.cs
 * Author: Dharsan.R
 * Date: September 30, 2023
 * Description: This file contains the definition of the Train class, which represents train information.
 * It includes properties for train ID, name, and status.
 */

using MongoDB.Bson;

namespace TrainManagement.Models
{
    //Train Class and its attribute
    public class Train
    {
        // Unique identifier for the train.
        public ObjectId Id { get; set; }

        
        public int TrainId { get; set; }

     
        public string TrainName { get; set; }

        
        public string TrainStatus { get; set; }
    }
}
