package me.axiometry.judgementalist

import akka.actor.Actor
import org.scala_tools.time.Imports._

trait Storage extends Actor

object Storage {
  object Requests {
    object Contests {
      case class Get(id: String)
      case class List(sortBy: SortOrder = SortOrder.StartDate,
                      ascending: Boolean = false,
                      offset: Int = 0,
                      count: Int = -1)
      case class Create(id: String,
                        name: String,
                        startDate: DateTime,
                        endDate: DateTime,
                        problemIds: Set[String])
      case class AddParticipants(id: String, participantIds: String*)
      case class Delete(ids: String*)

      sealed trait SortOrder
      object SortOrder {
        case object Id extends SortOrder
        case object Name extends SortOrder
        case object StartDate extends SortOrder
        case object EndDate extends SortOrder
      }
    }

    object Participants {
      case class Get(id: String)
      case class List(sortBy: SortOrder = SortOrder.Name,
                      ascending: Boolean = false,
                      offset: Int = 0,
                      count: Int = -1)
      case class CreateUser(id: String,
                            name: String)
      case class CreateTeam(id: String,
                            name: String,
                            memberIds: Set[String])
      case class Update(id: String, name: String)
      case class AddTeamMembers(id: String, memberIds: String*)
      case class RemoveTeamMembers(id: String, memberIds: String*)
      case class Delete(ids: String*)

      sealed trait SortOrder
      object SortOrder {
        case object Id extends SortOrder
        case object Name extends SortOrder
      }
    }

    object Problems {
      case class Get(id: String)
      case class GetCases(id: String, sampleOnly: Boolean = false)
      case class List(sortBy: SortOrder = SortOrder.Name,
                      ascending: Boolean = false,
                      offset: Int = 0,
                      count: Int = -1)
      case class Create(id: String,
                        name: String,
                        statement: String)
      case class AddCase(id: String,
                         index: Int,
                         input: String,
                         output: String,
                         timeLimit: Option[Long],
                         memoryLimit: Option[Long])
      case class Update(id: String, name: String = null, statement: String = null)
      case class Delete(ids: String*)
      case class DeleteCases(id: String, indices: Int*)

      sealed trait SortOrder
      object SortOrder {
        case object Id extends SortOrder
        case object Name extends SortOrder
        case object Difficulty extends SortOrder

      }
    }

    case class Search(query: String,
                      targets: Set[Search.Target] = Search.Target.values,
                      offset: Int = 0,
                      count: Int = -1)
    object Search {
      sealed trait Target
      object Target {
        case object Contests extends Target
        case object Participants extends Target
        case object Problems extends Target

        val values: Set[Target] = Set(Contests, Participants, Problems)
      }
    }
  }
  object Responses {
    object Contests {
      case class Get(contest: Contest)
      case class GetNotFound(id: String)
      case class List(contests: Seq[Contest])
      case class Create(contest: Contest)
      case class CreateFailure(reason: CreateFailure.Reason)
      object CreateFailure {
        sealed trait Reason
        object Reason {
          case class ConflictingId(id: String) extends Reason
          case class InvalidProblems(problemIds: String*) extends Reason
        }
      }
      case class AddParticipants(contest: Contest, participants: Participant*)
      case class Delete(ids: String*)
    }
    object Participants {
      case class Get(participant: Participant)
      case class GetNotFound(id: String)
      case class List(participants: Seq[Participant])
      case class CreateUser(user: User)
      case class CreateTeam(team: Team)
      case class CreateFailure(reason: CreateFailure.Reason)
      object CreateFailure {
        sealed trait Reason
        object Reason {
          case class ConflictingId(id: String) extends Reason
          case class InvalidTeamMembers(memberIds: String*) extends Reason
        }
      }
      case class Update(participant: Participant)
      case class AddTeamMembers(team: Team, members: User*)
      case class RemoveTeamMembers(team: Team, members: User*)
      case class TeamMembersFailure(reason: TeamMembersFailure.Reason)
      object TeamMembersFailure {
        sealed trait Reason
        object Reason {
          case class InvalidTeamMembers(memberIds: String*) extends Reason
        }
      }
      case class Delete(ids: String*)
    }
    object Problems {
      case class Get(problem: Problem)
      case class GetNotFound(id: String)
      case class GetCases(problem: Problem, cases: Seq[ProblemCase])
      case class GetCasesFailure(reason: GetCasesFailure.Reason)
      object GetCasesFailure {
        sealed trait Reason
        object Reason {
          case class InvalidProblem(id: String) extends Reason
        }
      }
      case class List(problems: Seq[Problem])
      case class Create(problem: Problem)
      case class CreateFailure(reason: CreateFailure.Reason)
      object CreateFailure {
        sealed trait Reason
        object Reason {
          case class ConflictingId(id: String) extends Reason
        }
      }
      case class AddCase(problemCase: ProblemCase)
      case class AddCaseFailure(reason: AddCaseFailure.Reason)
      object AddCaseFailure {
        sealed trait Reason
        object Reason {
          case class InvalidProblem(id: String) extends Reason
          case class ConflictingIndex(index: Int) extends Reason
        }
      }
      case class Update(problem: Problem)
      case class Delete(ids: String*)
      case class DeleteCases(problem: Problem, indices: Int*)
      case class DeleteCasesFailure(reason: DeleteCasesFailure.Reason)
      object DeleteCasesFailure {
        sealed trait Reason
        object Reason {
          case class InvalidProblem(id: String) extends Reason
        }
      }
    }

    case class Search(query: String,
                      contests: Seq[Contest],
                      participants: Seq[Participant],
                      problems: Seq[Problem])
  }
}