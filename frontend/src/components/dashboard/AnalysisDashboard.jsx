import React from "react";

import ScoreCard from "./ScoreCard";
import EvidenceCard from "./EvidenceCard";
import KPISection from "./KPISection";
import GapsSection from "./GapsSection";
import FollowUpSection from "./FollowUpSection";
import BiasSection from "./BiasSection";
import ConfidenceCard from "./ConfidenceCard";
import DependencyRiskCard from "./DependencyRiskCard";
import WhyHigherScore from "./WhyHigherScore";
import TranscriptHighlighter from "./TranscriptHighlighter";

export default function AnalysisDashboard({ data, loading }) {
  if (loading) {
    return (
  <div className="grid grid-cols-1 lg:grid-cols-2 gap-4 p-4 bg-gray-100 text-black">
        <div className="h-32 bg-gray-200 animate-pulse rounded" />
        <div className="h-40 bg-gray-200 animate-pulse rounded" />
        <div className="h-40 bg-gray-200 animate-pulse rounded" />
      </div>
    );
  }

  if (!data) {
  return (
    <div className="p-6 text-black bg-white rounded">
      No analysis available. Probably because nothing was analyzed yet.
    </div>
  );
}

  return (
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-4 p-4 bg-gray-50">
      <ScoreCard score={data.score} />
      <ConfidenceCard confidence={data.confidence} />

      <DependencyRiskCard risk={data.dependencyRisk} />
      <WhyHigherScore explanation={data.whyNotHigherScore} />

      <KPISection kpis={data.kpiMapping} />
      <GapsSection gaps={data.gaps} />

      <EvidenceCard evidence={data.evidence} />
      <BiasSection biases={data.supervisorBiases} />

      <FollowUpSection questions={data.followUpQuestions} />

      <div className="lg:col-span-2">
        <TranscriptHighlighter transcript={data.transcript} />
      </div>
    </div>
  );
}