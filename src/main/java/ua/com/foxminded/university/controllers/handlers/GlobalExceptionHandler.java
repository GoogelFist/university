package ua.com.foxminded.university.controllers.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String EXCEPTION_ATTRIBUTE_NAME = "exceptionMessage";
    private static final String EXCEPTION_VIEW_NAME = "error";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView notFound(EntityNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION_ATTRIBUTE_NAME, exception.getMessage());
        modelAndView.setViewName(EXCEPTION_VIEW_NAME);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView error(RuntimeException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION_ATTRIBUTE_NAME, exception.getMessage());
        modelAndView.setViewName(EXCEPTION_VIEW_NAME);
        return modelAndView;
    }
}