package me.axiometry.judgementalist

import org.scala_tools.time.Imports._

case class Contest(id: String,
                   name: String,
                   startDate: Long,
                   endDate: Long,
                   problems: Seq[Problem],
                   participants: Set[Participant])