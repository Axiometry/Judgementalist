package me.axiometry.judgementalist

case class ProblemCase(problem: Problem,
                       index: Int,
                       input: String,
                       output: String,
                       isSample: Boolean,
                       timeLimit: Option[Long],
                       memoryLimit: Option[Long])