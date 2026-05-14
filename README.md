# Trinethra — Supervisor Feedback Analyzer

## Overview

Trinethra is a local AI-powered full-stack application designed to analyze supervisor feedback transcripts of Fellows working in manufacturing organizations. It leverages a locally running Large Language Model (via Ollama) to transform unstructured conversational feedback into structured operational insights.

The system generates:

- Performance scoring (rubric-based)
- Evidence extraction with traceable quotes
- KPI mapping
- Gap detection across multiple dimensions
- Follow-up questions for supervisors
- Bias detection and confidence estimation

It is designed as a decision-support tool for psychology interns and evaluators, not as a replacement for human judgment.

---

## Tech Stack

### Backend
- Java 17
- Spring Boot
- REST APIs
- WebClient (Ollama integration)
- Jackson (JSON processing)

### Frontend
- React (Vite)
- Tailwind CSS (optional styling)

### AI Layer
- Ollama (local LLM runtime)
- Model: llama3.2

---

## Architecture Overview

The system follows a modular pipeline-based architecture:

1. **Frontend Input Layer**
   - User submits supervisor transcript via UI

2. **Backend Processing Layer**
   - Spring Boot receives request via `/api/analyze`

3. **AI Analysis Pipeline**
   - Prompt construction via structured templates
   - Ollama LLM inference (local execution)
   - JSON response generation

4. **Post-processing Layer**
   - JSON validation and cleanup
   - DTO mapping
   - Confidence normalization

5. **Analysis Output Layer**
   - Evidence extraction
   - Rubric-based scoring
   - KPI mapping
   - Gap detection
   - Bias detection
   - Follow-up generation

6. **Frontend Visualization**
   - Modular dashboard renders structured insights

---

## Setup Instructions

### 1. Install Ollama

Download and install:
https://ollama.com

Pull required model:

```bash
ollama pull llama3.2

Test installation:

ollama run llama3.2 "Hello"

Ollama runs locally at:

http://localhost:11434
2. Backend Setup (Spring Boot)
cd backend
mvn clean install
mvn spring-boot:run

Backend runs at:

http://localhost:8080

Primary API endpoint:

POST /api/analyze
3. Frontend Setup (React + Vite)
cd frontend
npm install
npm run dev

Frontend runs at:

http://localhost:3000