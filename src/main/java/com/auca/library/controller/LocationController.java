package com.auca.library.controller;

import com.auca.library.model.Location;
import com.auca.library.service.LocationService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class LocationController extends HttpServlet {
    private LocationService locationService;

    @Override
    public void init() {
        locationService = new LocationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createLocation(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listLocations(request, response);
        }
    }

    private void createLocation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String sector = request.getParameter("sector");
        String cell = request.getParameter("cell");
        String village = request.getParameter("village");

        Location location = new Location(0, province, district, sector, cell, village);
        boolean isCreated = locationService.createLocation(location);

        if (isCreated) {
            response.sendRedirect("dashboard.jsp?status=location_created");
        } else {
            response.sendRedirect("location.jsp?status=failed");
        }
    }

    private void listLocations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Location> locations = locationService.getAllLocations();
        request.setAttribute("locations", locations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("locations_list.jsp");
        dispatcher.forward(request, response);
    }
}

