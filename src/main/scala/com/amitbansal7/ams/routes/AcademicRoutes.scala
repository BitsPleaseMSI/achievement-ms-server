package com.amitbansal7.ams.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.amitbansal.ams.config.JsonSupport._
import com.amitbansal7.ams.services.AcademicService

object AcademicRoutes {

  def route = {
    pathPrefix("academic") {
      (path("add") & post) {
        formField(
          'rollNo,
          'name,
          'batch,
          'programme,
          'token,
          'category
        ) { (rollNo, name, batch, programme, token, category) =>
            complete(AcademicService.add(rollNo, name, batch, programme, category, token))
          }
      } ~ (path("delete") & post) {
        formField('id, 'token) { (id, token) =>
          complete(AcademicService.deleteOne(id, token))
        }
      } ~ (path("getall") & get) {
        complete(AcademicService.getAll())
      } ~ (path("edit") & put) {
        formField(
          'id,
          'rollNo,
          'name,
          'batch,
          'programme,
          'token,
          'category
        ) { (id, rollNo, name, batch, programme, token, category) =>
            complete(AcademicService.edit(id, rollNo, name, batch, programme, category, token))
          }
      }
    }
  }
}
