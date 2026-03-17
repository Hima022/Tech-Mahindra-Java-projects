package com.tmf.servlets.entity;


import java.util.List;

import jakarta.servlet.jsp.tagext.TagSupport;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.JspException;

public class BookingTableTag extends TagSupport {

    private List<Booking> bookings;

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public int doStartTag() throws JspException {

        try {

            JspWriter out = pageContext.getOut();

            if (bookings == null || bookings.isEmpty()) {

                out.println("<tr>");
                out.println("<td colspan='5'>No bookings available</td>");
                out.println("</tr>");

                return SKIP_BODY;
            }

            for (Booking b : bookings) {

                out.println("<tr>");

                out.println("<td>" + b.getBookingId() + "</td>");
                out.println("<td>" + b.getSource() + "</td>");
                out.println("<td>" + b.getDestination() + "</td>");
                out.println("<td>" + b.getStatus() + "</td>");
                out.println("<td>" + b.getPrice() + "</td>");

                out.println("</tr>");
            }

        } catch (Exception e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}