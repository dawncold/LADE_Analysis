package domain.service

import domain.dto.VMethodInvoke
import domain.model.JMethod

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

trait MethodBaseService extends BaseDependenceService {
  self: ApplicationService =>

  protected def buildCallerSize(method:JMethod):JMethod = {
    val mf = repo.countCallers(method.id)
    val mmf = mf.map(v => method.copy(callerSize = v))
    Await.result(mmf,Duration.Inf)
  }

  def searchMethodsByCritical(keys: Seq[String]):Future[Seq[JMethod]] = {
    repo.searchMethods(keys)
  }

  def loadATargetInvokeBTarget(aTarget:String,bTarget:String): Future[Seq[VMethodInvoke]] = repo.loadATargetInvokeBTarget(aTarget,bTarget)

}
