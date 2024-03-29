package com.google.samples.apps.iosched.shared.di

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import javax.inject.Scope

/**
 * The ServiceScoped custom scoping annotation specifies that the lifespan of a dependency be
 * the same as that of a Service. This is used to annotate dependencies that behave like a
 * singleton within the lifespan of a Service.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE, ElementType.METHOD)
annotation class ServiceScoped
