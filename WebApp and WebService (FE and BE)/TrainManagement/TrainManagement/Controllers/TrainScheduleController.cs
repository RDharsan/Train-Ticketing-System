/*
 * Filename: TrainScheduleController.cs
 * Author: Dharsan.R
 * Date: October 01, 2023
 * Description: This file contains the definition of the TrainScheduleController class, which is responsible for handling Train Schedules.
 */

using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using MongoDB.Driver;
using System.Linq;
using TrainManagement.Models;

namespace TrainManagement.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TrainScheduleController : ControllerBase
    {
        private readonly IConfiguration _configuration;
        //Initialize new instance of the TrainSchedule Controller
        public TrainScheduleController(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        //Get All Train Schedules
        [HttpGet]
        public JsonResult Get()
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));
            var dbList = dbClient.GetDatabase("EAD_Railway").GetCollection<TrainSchedule >("TrainScheduleTbl").AsQueryable();
            return new JsonResult(dbList);
        }

        //Insert New Train Schedule
        [HttpPost]
        public JsonResult Post(TrainSchedule trainSchedule)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));
            int LastTrainScheduleId = dbClient.GetDatabase("EAD_Railway").GetCollection<TrainSchedule>("TrainScheduleTbl").AsQueryable().Count();
            trainSchedule.TrainScheduleId = LastTrainScheduleId + 1;
            dbClient.GetDatabase("EAD_Railway").GetCollection<TrainSchedule>("TrainScheduleTbl").InsertOne(trainSchedule);
            return new JsonResult("Added Successfully");
        }

        //Update Exisiting Train Schedule
        [HttpPut]
        public JsonResult Put(TrainSchedule trainSchedule)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));
            var filter = Builders<TrainSchedule>.Filter.Eq("TrainScheduleId", trainSchedule.TrainScheduleId);
            var update = Builders<TrainSchedule>.Update
                .Set("TrainName", trainSchedule.TrainName)
                .Set("TrainDate", trainSchedule.TrainDate)
                .Set("TrainSource", trainSchedule.TrainSource)
                .Set("TrainDest", trainSchedule.TrainDest);

            dbClient.GetDatabase("EAD_Railway").GetCollection<TrainSchedule>("TrainScheduleTbl").UpdateOne(filter, update);
            return new JsonResult("Updated Successfully");
        }

        //Delete Train Schedule
        [HttpDelete("{trainScheduleId}")]
        public JsonResult Delete(int trainScheduleId)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));
            var filter = Builders<TrainSchedule>.Filter.Eq("TrainScheduleId", trainScheduleId);
            dbClient.GetDatabase("EAD_Railway").GetCollection<TrainSchedule>("TrainScheduleTbl").DeleteOne(filter);
            return new JsonResult("Deleted Successfully");
        }
    }
}
