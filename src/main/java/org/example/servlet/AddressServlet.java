package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.AddressRequest;
import org.example.dto.EmployeeRequest;
import org.example.enums.StorageType;
import org.example.exception.AddressNotFoundException;
import org.example.model.Address;
import org.example.service.AddressService;
import org.example.service.impl.AddressServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AddressServlet extends HttpServlet {

    AddressService addressService = new AddressServiceImpl(StorageType.DATABASE);

    private static final Logger logger = LoggerFactory.getLogger(AddressServlet.class);


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        System.out.println("Initialized");
        logger.info("EmployeeServlet initialized");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        String jsonAddress = null;
        try {
            List<Address> addressList = addressService.getAllAddresses();
            jsonAddress = gson.toJson(addressList);
        } catch (AddressNotFoundException e) {
            throw new RuntimeException(e);
        }

        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();

        printWriter.println(jsonAddress);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        String jsonResponse;
        int addressId = 0;

        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String jsonString = stringBuilder.toString();
        EmployeeRequest employeeRequest = gson.fromJson(jsonString, EmployeeRequest.class);

        AddressRequest addressRequest = new AddressRequest(employeeRequest.getLocation(), employeeRequest.getPin());
        try {
            addressId = addressService.addAddress(addressRequest);
            jsonResponse = gson.toJson(addressId);
        } catch (AddressNotFoundException e) {
            throw new RuntimeException(e);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        String jsonResponse = null;
        try {
            BufferedReader reader = req.getReader();
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
            Address address = gson.fromJson(jsonStringBuilder.toString(), Address.class);
            AddressRequest addressRequest = new AddressRequest(address.getLocation(), address.getPin());

            addressService.updateAddress(address.getId(), addressRequest);
            jsonResponse = gson.toJson(address);
        } catch (Exception e) {
            logger.error(e.toString());
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String jsonResponse = null;
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
            addressService.deleteAddress(id);
            jsonResponse = gson.toJson(id);
        } catch (Exception e) {
            logger.info(e.toString());
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(jsonResponse);
    }
}
