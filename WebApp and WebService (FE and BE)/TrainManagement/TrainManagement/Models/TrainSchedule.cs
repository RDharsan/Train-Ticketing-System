/*
 * Filename: TrainSchedule.cs
 * Author: Dharsan.R
 * Date: September 30, 2023
 * Description: This file contains the definition of the TrainSchedule class, which represents train schedule information.
 * It includes properties for train schedule ID, train name, date, source, and destination.
 */

using MongoDB.Bson;

namespace TrainManagement.Models
{
    // TrainSchedule Class and its attributes
    public class TrainSchedule
    {
        
        public ObjectId Id { get; set; }

        
        public int TrainScheduleId { get; set; }

        
        public string TrainName { get; set; }

        
        public string TrainDate { get; set; }

       
        public string TrainSource { get; set; }

        
        public string TrainDest { get; set; }
    }
}
