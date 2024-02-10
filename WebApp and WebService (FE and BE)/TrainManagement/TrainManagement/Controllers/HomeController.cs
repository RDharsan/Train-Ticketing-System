/*
 * Filename: HomeController.cs
 * Author: Dharsan.R
 * Date: September 30, 2023
 * Description: This file contains the definition of the HomeController class, which is responsible for handling default views.
 */

using Microsoft.AspNetCore.Mvc;

namespace TrainManagement.Controllers
{
    /// Represents the home controller responsible for handling default views.
    public class HomeController : Controller
    {
        /// Displays the default index view.
        public IActionResult Index()
        {
            
            return View();
        }
    }
}
