package org.stx;

import junit.framework.TestCase;
import org.junit.Assert;

interface I1 { }
interface I2 { }
interface I3 { }
interface I4 { }

class I1Impl implements I1 { }
class I2Impl implements I2 { }
class I3Impl implements I3 { }
class I4Impl implements I4 { }

public class ServiceRegistrationTest extends TestCase {
    public void testServiceRegistration1() {
        ServiceRegistration registration = new ServiceRegistration();
        registration.registrationService(I1.class, new I1Impl());
        I1 i1 = registration.getService(I1.class);
        Assert.assertNotNull(i1);
    }

    public void testServiceRegistration2() {
        ServiceRegistration registration = new ServiceRegistration();
        registration.registrationService(I2.class, org.stx.I2Impl.class);
        I2 i2 = registration.getService(I2.class);
        Assert.assertNotNull(i2);
    }

    public void testServiceRegistration3() {
        ServiceRegistration registration = new ServiceRegistration();
        registration.registrationService(I3.class, "org.stx.I3Impl");
        I3 i3 = registration.getService(I3.class);
        Assert.assertNotNull(i3);
    }

    public void testServiceRegistration4() {
        ServiceRegistration registration = new ServiceRegistration();
        registration.registrationService(I3.class, "org.stx.I2Impl");
        registration.registrationService(I4.class, new I4Impl());
        registration.registrationService(I2.class, new I2Impl());
    }

    public void testServiceRegistration5() {
        ServiceRegistration registration = new ServiceRegistration();
        registration.registrationService(I3.class, "org.stx.sdsdsdI2Impl");
    }

    public void testServiceRegistration6() {
        ServiceRegistration registration = new ServiceRegistration();
        I1 i1 = registration.getService(null);
    }

    public void testUnregisterService() {
        ServiceRegistration registration = new ServiceRegistration();
        registration.registrationService(I1.class, org.stx.I1Impl.class);
        registration.unregisterService(I1.class);
    }

    public void testGetUnregisterService1() {
        ServiceRegistration registration = new ServiceRegistration();
        registration.registrationService(I3.class, new I3Impl());
        registration.unregisterService(I2.class);

        I2 i2 = registration.getService(I2.class);
        Assert.assertNull(i2);
    }

    public void testGetUnregisterService2() {
        ServiceRegistration registration = new ServiceRegistration();

        I3 i3 = registration.getService(I3.class);
        Assert.assertNull(i3);
    }
}
