package edu.kit.kastel.sdq.coupling.patternbased.sdg;

/**
 * Represents the different edge types of a sdg.
 *
 * @author Laura
 *
 */
public enum SdgEdgeType {
  CL, // CALL
  DD, // DATA DEPENDENCY
  PS, // PARAMETER STRUCTURE
  CF, // CONTROL FLOW
  UNSPECIFIED
}
