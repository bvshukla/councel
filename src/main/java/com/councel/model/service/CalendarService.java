package com.councel.model.service;

import com.councel.model.pojo.ClientAppointments;
import com.councel.model.pojo.User;
import com.councel.model.utils.DBUtils;

public class CalendarService {

	public static Long createAppointment(ClientAppointments appointment){
		Long appId = (Long) DBUtils.save(appointment);
		return appId;
	}
	
	public static ClientAppointments findAppointment(Long id){
		ClientAppointments res = null;
		res = DBUtils.findByKey(ClientAppointments.class, id);
		return res;

	}
	
	public static ClientAppointments updateAppointment(ClientAppointments appointment){
		ClientAppointments res = null;
		DBUtils.update(appointment);
		res = findAppointment(appointment.getAppointmentId());
		return res;

	}
	
	public static void deleteAppointment(ClientAppointments appointment){
		ClientAppointments res = null;
		DBUtils.delete(appointment);

	}
}
