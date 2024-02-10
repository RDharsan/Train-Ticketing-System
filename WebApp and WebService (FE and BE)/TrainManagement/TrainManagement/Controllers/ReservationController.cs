/*
 * Filename: ReservationController.cs
 * Author: Dharsan.R
 * Date: October 02, 2023
 * Description: This file contains the definition of the ReservationController class, which is responsible for handling Reservations.
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
    public class ReservationController : ControllerBase
    {
        private readonly IConfiguration _configuration;
        //Initialize new instance of Reservation Controller
        public ReservationController(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        // Get All Reservations
        [HttpGet]
        public JsonResult Get()
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));
            var dbList = dbClient.GetDatabase("EAD_Railway").GetCollection<Reservation>("ReservationTbl").AsQueryable();

            return new JsonResult(dbList);
        }

        //Insert New Reservation Details
        [HttpPost]
        public JsonResult Post(Reservation reservation)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));
            int LastReservationId = dbClient.GetDatabase("EAD_Railway").GetCollection<Reservation>("ReservationTbl").AsQueryable().Count();
            reservation.ReservationId = LastReservationId + 1;

            dbClient.GetDatabase("EAD_Railway").GetCollection<Reservation>("ReservationTbl").InsertOne(reservation);
            return new JsonResult("Added Successfully");
        }

        //Update Exisiting Reservation Record
        [HttpPut]
        public JsonResult Put(Reservation reservation)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));

            var filter = Builders<Reservation>.Filter.Eq("ReservationId", reservation.ReservationId);

            var update = Builders<Reservation>.Update
                .Set("NIC", reservation.NIC)
                .Set("TrainScheduleId", reservation.TrainScheduleId);


            dbClient.GetDatabase("EAD_Railway").GetCollection<Reservation>("TraiReservationTblnTbl").UpdateOne(filter, update);
            return new JsonResult("Updated Successfully");
        }

        //Delete Reservation
        [HttpDelete("{reservationId}")]
        public JsonResult Delete(int reservationId)
        {
            MongoClient dbClient = new MongoClient(_configuration.GetConnectionString("TrainAppCon"));

            var filter = Builders<Reservation>.Filter.Eq("ReservationId", reservationId);


            dbClient.GetDatabase("EAD_Railway").GetCollection<Reservation>("ReservationTbl").DeleteOne(filter);
            return new JsonResult("Deleted Successfully");
        }
    }
}

