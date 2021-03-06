package com.amitbansal7.ams.repositories

import com.amitbansal.ams.config.MongoConfig
import com.amitbansal7.ams.models.Achievement
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.result

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._

class AchievementRepository {

  val achievementCollection = MongoConfig.getachievementCollection

  def addAchievement(ach: Achievement) =
    achievementCollection.insertOne(ach).toFuture()

  def findById(id: ObjectId) =
    achievementCollection
      .find(Document("_id" -> id))
      .first().toFuture()

  def approve(id: ObjectId, approved: Boolean): Future[result.UpdateResult] =
    achievementCollection.updateOne(
      Document("_id" -> id),
      Document("$set" -> Document("approved" -> approved))
    ).toFuture()

  def approveByUser(id: ObjectId, userEmail: String): Future[result.UpdateResult] =
    achievementCollection.updateOne(
      Document("_id" -> id),
      Document("$set" -> Document("approved" -> true, "approvedBy" -> userEmail))
    ).toFuture()

  def findAllByUnApprovedDepartmentAndDepartment(department: String, shift: String) =
    achievementCollection
      .find(
        Document("department" -> department, "approved" -> false, "shift" -> shift)
      ).toFuture()

  def deleteOne(id: ObjectId) =
    achievementCollection.
      deleteOne(
        Document("_id" -> id)
      ).toFuture()

  def findAllApprovedByDepartment(department: String) =
    achievementCollection
      .find(
        Document("department" -> department, "approved" -> true)
      ).toFuture()

  def findAllApproved(offset: Option[Int], limit: Option[Int]) =
    achievementCollection
      .find(
        Document("approved" -> true)
      ).toFuture()
}
