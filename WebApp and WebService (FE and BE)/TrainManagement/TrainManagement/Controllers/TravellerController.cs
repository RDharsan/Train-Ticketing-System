/*
 * Filename: Traveller.cs
 * Author: Dharsan.R
 * Date: October 02, 2023
 * Description: This file contains the definition of the Traveller class, which is responsible for handling Travellers.
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
    public class TravellerController : ControllerBase
    {

        private readonly IConfiguration _configuration;
        //Initialize new Traveller controller
        public TravellerController(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        // Get All Travellers
        [HttpGet]
        public JsonResult Get()
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));
            var dbList = dbClient.GetDatabase("EAD_Railway").GetCollection<Traveller>("Traveller").AsQueryable();

            return new JsonResult(dbList);
        }

        // Insert new Traveller
        [HttpPost]
        public JsonResult Post(Traveller traveller)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));

            dbClient.GetDatabase("EAD_Railway").GetCollection<Traveller>("Traveller").InsertOne(traveller);
            return new JsonResult("Added Successfully");
        }

        //update Traveller
        [HttpPut]
        public JsonResult Put(Traveller traveller)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));

            var filter = Builders<Traveller>.Filter.Eq("NIC", traveller.NIC);

            var update = Builders<Traveller>.Update
                .Set("Name", traveller.Name)
                .Set("Address", traveller.Address)
                .Set("Phone", traveller.Phone);


            dbClient.GetDatabase("EAD_Railway").GetCollection<Traveller>("Traveller").UpdateOne(filter, update);
            return new JsonResult("Updated Successfully");
        }

        //Delete Traveller by NIC
        [HttpDelete("{nic}")]
        public JsonResult Delete(string nic)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));

            var filter = Builders<Traveller>.Filter.Eq("NIC", nic);


            dbClient.GetDatabase("EAD_Railway").GetCollection<Traveller>("Traveller").DeleteOne(filter);
            return new JsonResult("Deleted Successfully");
        }
    }
}
