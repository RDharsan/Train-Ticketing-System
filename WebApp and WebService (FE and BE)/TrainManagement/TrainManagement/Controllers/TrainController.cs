/*
 * Filename: HomeController.cs
 * Author: Dharsan.R
 * Date: October 01, 2023
 * Description: This file contains the definition of the TrainController class, which is responsible for handling Train CRUD.
 */

using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using MongoDB.Driver;
using System.Collections.Generic; 
using System.Linq;
using TrainManagement.Models;

namespace TrainManagement.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TrainController : ControllerBase
    {
        private readonly IConfiguration _configuration;
        public TrainController(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        // Retrieves a list of all trains.
        [HttpGet]
        public JsonResult Get()
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));
            var trainCollection = dbClient.GetDatabase("EAD_Railway").GetCollection<Train>("TrainTbl");

            List<Train> trains = trainCollection.Find(_ => true).ToList(); // Retrieve all records

            return new JsonResult(trains);
        }

        // Adds a new train record.
        [HttpPost]
        public JsonResult Post(Train train)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));
            int LastTrainId = dbClient.GetDatabase("EAD_Railway").GetCollection<Train>("TrainTbl").AsQueryable().Count();
            train.TrainId = LastTrainId + 1;

            dbClient.GetDatabase("EAD_Railway").GetCollection<Train>("TrainTbl").InsertOne(train);
            return new JsonResult("Added Successfully");
        }

        //Update Train Record
        [HttpPut]
        public JsonResult Put(Train train)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));

            var filter = Builders<Train>.Filter.Eq("TrainId", train.TrainId);

            var update = Builders<Train>.Update
                .Set("TrainName", train.TrainName)
                .Set("TrainStatus", train.TrainStatus);


            dbClient.GetDatabase("EAD_Railway").GetCollection<Train>("TrainTbl").UpdateOne(filter,update);
            return new JsonResult("Updated Successfully");
        }

        //Delete Train Record
        [HttpDelete("{id}")]
        public JsonResult Delete(int id) 
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));

            var filter = Builders<Train>.Filter.Eq("TrainId", id);


            dbClient.GetDatabase("EAD_Railway").GetCollection<Train>("TrainTbl").DeleteOne(filter);
            return new JsonResult("Deleted Successfully");
        }
    }
}
