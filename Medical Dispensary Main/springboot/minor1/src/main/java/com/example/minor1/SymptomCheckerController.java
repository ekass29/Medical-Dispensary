package com.example.minor1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000") // Replace 3000 with your Node.js port
@RestController
@RequestMapping("/api")
public class SymptomCheckerController {

    private static final List<DiseaseInfo> diseases = new ArrayList<>();

    static {
        // Initialize diseases with symptoms, cure, and doctor type
        diseases.add(new DiseaseInfo("Flu", Set.of("Fever", "Cough", "Fatigue", "Sore throat", "Muscle aches", "Chills"),
                "Rest, hydration, fever reducers", "General Practitioner"));
        diseases.add(new DiseaseInfo("Cold", Set.of("Cough", "Runny nose", "Mild fever", "Sore throat", "Congestion", "Sneezing"),
                "Over-the-counter medicines", "General Practitioner"));
        diseases.add(new DiseaseInfo("COVID-19", Set.of("Fever", "Cough", "Shortness of breath", "Loss of taste or smell", "Fatigue", "Headache"),
                "Quarantine, rest, monitor symptoms", "Infectious Disease Specialist"));
        diseases.add(new DiseaseInfo("Bronchitis", Set.of("Cough", "Chest pain", "Fatigue", "Cough with mucus", "Shortness of breath", "Wheezing"),
                "Hydration, cough suppressants, rest", "Pulmonologist"));
        diseases.add(new DiseaseInfo("Pneumonia", Set.of("Fever", "Cough", "Shortness of breath", "Chest tightness", "Fatigue", "Chills"),
                "Antibiotics, rest, oxygen therapy", "Pulmonologist"));
        diseases.add(new DiseaseInfo("Sinusitis", Set.of("Headache", "Runny nose", "Facial pain", "Sore throat", "Nasal congestion", "Fatigue"),
                "Decongestants, nasal sprays, rest", "ENT Specialist"));
        diseases.add(new DiseaseInfo("Asthma", Set.of("Wheezing", "Cough", "Shortness of breath", "Chest tightness", "Fatigue", "Sore throat"),
                "Inhalers, bronchodilators, avoid triggers", "Pulmonologist"));
        diseases.add(new DiseaseInfo("Tuberculosis", Set.of("Coughing up blood", "Fever", "Night sweats", "Weight loss", "Fatigue", "Chills"),
                "Antibiotics, isolation, rest", "Infectious Disease Specialist"));
        diseases.add(new DiseaseInfo("Migraine", Set.of("Severe headache", "Nausea", "Sensitivity to light", "Fatigue", "Dizziness", "Blurred vision"),
                "Pain relievers, rest, stress management", "Neurologist"));
        diseases.add(new DiseaseInfo("Meningitis", Set.of("Severe headache", "Fever", "Stiff neck", "Nausea", "Fatigue", "Sensitivity to light"),
                "Hospitalization, antibiotics", "Neurologist"));
        diseases.add(new DiseaseInfo("Dengue", Set.of("Fever", "Chills", "Rash", "Joint pain", "Fatigue", "Nausea"),
                "Fluids, pain relievers, rest", "Infectious Disease Specialist"));
        diseases.add(new DiseaseInfo("Malaria", Set.of("Fever", "Chills", "Fatigue", "Headache", "Nausea", "Sweating"),
                "Antimalarial drugs, hydration, rest", "Infectious Disease Specialist"));
        diseases.add(new DiseaseInfo("Lyme Disease", Set.of("Rash", "Fever", "Joint pain", "Headache", "Fatigue", "Muscle aches"),
                "Antibiotics, rest", "Infectious Disease Specialist"));
        diseases.add(new DiseaseInfo("Zika Virus", Set.of("Fever", "Rash", "Joint pain", "Red eyes", "Fatigue", "Headache"),
                "Rest, hydration, pain relief", "General Practitioner"));
        diseases.add(new DiseaseInfo("Gastritis", Set.of("Nausea", "Vomiting", "Abdominal pain", "Loss of appetite", "Bloating", "Indigestion"),
                "Antacids, avoid spicy foods, rest", "Gastroenterologist"));
        diseases.add(new DiseaseInfo("GERD", Set.of("Chest pain", "Nausea after eating", "Trouble swallowing", "Heartburn", "Regurgitation", "Cough"),
                "Antacids, dietary changes, avoid triggers", "Gastroenterologist"));
        diseases.add(new DiseaseInfo("UTI", Set.of("Painful urination", "Frequent urination", "Abdominal pain", "Fever", "Cloudy urine", "Strong-smelling urine"),
                "Antibiotics, hydration", "Urologist"));
        diseases.add(new DiseaseInfo("Kidney Infection", Set.of("Fever", "Back pain", "Nausea", "Painful urination", "Confusion", "Chills"),
                "Antibiotics, hydration", "Nephrologist"));
        diseases.add(new DiseaseInfo("Appendicitis", Set.of("Abdominal pain", "Nausea", "Vomiting", "Fever", "Cramps", "Loss of appetite"),
                "Surgery, pain relief", "Surgeon"));
        diseases.add(new DiseaseInfo("Pancreatitis", Set.of("Abdominal pain", "Nausea", "Vomiting", "Loss of appetite", "Fever", "Jaundice"),
                "Hospitalization, pain relief, IV fluids", "Gastroenterologist"));
        diseases.add(new DiseaseInfo("Anemia", Set.of("Fatigue", "Paleness", "Shortness of breath", "Cold hands", "Dizziness", "Headache"),
                "Iron supplements, dietary changes", "Hematologist"));
        diseases.add(new DiseaseInfo("Hepatitis A", Set.of("Fever", "Nausea", "Abdominal pain", "Loss of appetite", "Fatigue", "Jaundice"),
                "Rest, hydration, avoid alcohol", "Hepatologist"));
        diseases.add(new DiseaseInfo("Chikungunya", Set.of("Fever", "Joint pain", "Rash", "Fatigue", "Headache", "Muscle pain"),
                "Rest, fluids, pain relievers", "Infectious Disease Specialist"));
        diseases.add(new DiseaseInfo("Tetanus", Set.of("Muscle stiffness", "Difficulty swallowing", "Seizures", "Fever", "Headache", "Sweating"),
                "Antitoxins, hospitalization", "Neurologist"));
        diseases.add(new DiseaseInfo("HIV/AIDS", Set.of("Weight loss", "Night sweats", "Fatigue", "Swelling", "Fever", "Diarrhea"),
                "Antiretroviral therapy, supportive care", "Infectious Disease Specialist"));
        diseases.add(new DiseaseInfo("Psoriasis", Set.of("Dry skin", "Rash", "Itchy skin", "Red patches", "Flaking skin", "Cracking skin"),
                "Topical treatments, UV therapy", "Dermatologist"));
        diseases.add(new DiseaseInfo("Eczema", Set.of("Itchy skin", "Dry skin", "Rash", "Redness", "Cracking", "Inflammation"),
                "Moisturizers, antihistamines", "Dermatologist"));
        diseases.add(new DiseaseInfo("Hypothyroidism", Set.of("Fatigue", "Weight gain", "Cold hands", "Dry skin", "Swelling", "Constipation"),
                "Hormone therapy, lifestyle changes", "Endocrinologist"));
        diseases.add(new DiseaseInfo("Stroke", Set.of("Confusion", "Numbness", "Loss of coordination", "Blurred vision", "Severe headache", "Speech difficulties"),
                "Emergency care, rehabilitation", "Neurologist"));
    }

    // Root Decision Tree Node
    private static final DecisionTreeNode decisionTreeRoot = buildDecisionTree();

    // Endpoint for diagnosing disease
    @PostMapping("/diagnose")
    public Map<String, String> diagnoseDisease(@RequestBody List<String> symptoms) {
        // Traverse the decision tree to diagnose the disease
        String diagnosedDisease = traverseDecisionTree(decisionTreeRoot, symptoms);

        // Fetch the matched disease info
        DiseaseInfo info = diseases.stream()
                .filter(d -> d.name.equals(diagnosedDisease))
                .findFirst()
                .orElse(new DiseaseInfo("Unknown", Set.of(), "N/A", "N/A"));

        Map<String, String> response = new HashMap<>();
        response.put("disease", info.name);
        response.put("commonSymptoms", String.join(", ", info.symptoms));
        response.put("cure", info.cure);
        response.put("doctorType", info.doctorType);

        return response;
    }

    // Build a simple decision tree (manually or dynamically)
    private static DecisionTreeNode buildDecisionTree() {
        // Root Node
        DecisionTreeNode root = new DecisionTreeNode("Fever");

        // First-level branches
        root.addYesBranch(new DecisionTreeNode("Cough"));
        root.addNoBranch(new DecisionTreeNode("Severe headache"));

        // Second-level branches
        root.getYesBranch().addYesBranch(new DecisionTreeNode("Flu"));
        root.getYesBranch().addNoBranch(new DecisionTreeNode("Cold"));

        root.getNoBranch().addYesBranch(new DecisionTreeNode("Migraine"));
        root.getNoBranch().addNoBranch(new DecisionTreeNode("Unknown"));

        return root;
    }

    // Traverse the decision tree based on user symptoms
    private static String traverseDecisionTree(DecisionTreeNode node, List<String> symptoms) {
        if (node == null) return "Unknown";

        if (node.isLeaf()) {
            return node.getValue(); // Return the diagnosed disease at the leaf
        }

        // Check if the current symptom exists in the user-provided symptoms
        if (symptoms.contains(node.getValue())) {
            return traverseDecisionTree(node.getYesBranch(), symptoms);
        } else {
            return traverseDecisionTree(node.getNoBranch(), symptoms);
        }
    }

    // DecisionTreeNode Class
    private static class DecisionTreeNode {
        private final String value; // Symptom or Disease
        private DecisionTreeNode yesBranch;
        private DecisionTreeNode noBranch;

        public DecisionTreeNode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void addYesBranch(DecisionTreeNode node) {
            this.yesBranch = node;
        }

        public void addNoBranch(DecisionTreeNode node) {
            this.noBranch = node;
        }

        public DecisionTreeNode getYesBranch() {
            return yesBranch;
        }

        public DecisionTreeNode getNoBranch() {
            return noBranch;
        }

        public boolean isLeaf() {
            return yesBranch == null && noBranch == null;
        }
    }

    // DiseaseInfo Record
    record DiseaseInfo(String name, Set<String> symptoms, String cure, String doctorType) {}
}
