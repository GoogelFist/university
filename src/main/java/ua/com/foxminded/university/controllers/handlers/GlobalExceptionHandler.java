package ua.com.foxminded.university.controllers.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ua.com.foxminded.university.service.exceptions.ServiceException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String EXCEPTION_ATTRIBUTE_NAME = "exception";
    private static final String EXCEPTION_VIEW_NAME = "error";

    @ExceptionHandler(ServiceException.class)
    public ModelAndView serviceException(ServiceException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION_ATTRIBUTE_NAME, exception);
        modelAndView.setViewName(EXCEPTION_VIEW_NAME);
        return modelAndView;
    }
}