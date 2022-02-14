package org.stx;

import junit.framework.TestCase;

import static org.stx.ServiceRegistration.INST;

interface I1 { }
interface I2 { }
interface I3 { }
interface I4 { }

class I1Impl implements I1 { }
class I2Impl implements I2 { }
class I3Impl implements I3 { }
class I4Impl implements I4 { }

public class ServiceRegistrationTest extends TestCase {

    public void testGetServiceNPE() {
        I1 i1 = INST.getService(null); // NPE
    }

    public void testGetService() {
        INST.registrationService(I1.class, new I1Impl());

        I1 i1 = INST.getService(I1.class);
        I2 i2 = INST.getService(I2.class);

        assertNotNull(i1);
        assertNull(i2);
    }

    public void testRegistrationService1NPE() {
        I1Impl i1 = null;
        INST.registrationService(I1.class, i1); // NPE
    }

    public void testRegistrationService1() {
        INST.registrationService(I1.class, new I1Impl());
    }

    public void testRegistrationService2() {
        INST.registrationService(I2.class, "org.stx.I2Impl");
    }

    public void testRegistrationService2NPE() {
        String s = null;
        INST.registrationService(I1.class, s); // NPE
    }

    public void testRegistrationService3() {
        INST.registrationService(I4.class, org.stx.I4Impl.class);
    }

    public void testUnregisterServiceNPE() {
        INST.unregisterService(null);
    }

    public void testUnregisterService() {
        INST.unregisterService(I1.class);
    }

    public void testValues() {

    }

    public void testValueOf() {
    }
}