/**
 *  Copyright 2020 - 2021 The JfrUnit authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package dev.morling.jfrunit;

import jdk.jfr.consumer.RecordedClassLoader;

import java.util.function.Predicate;

public class ExpectedClassLoader implements Predicate<RecordedClassLoader> {
    private Long id;
    private String name;
    private ExpectedClass type;

    public ExpectedClassLoader() {
    }

    public ExpectedClassLoader(ClassLoader classLoader) {
        this.name = classLoader.getName();
        this.type = new ExpectedClass(classLoader.getClass());
    }

    @Override
    public boolean test(RecordedClassLoader recordedClassLoader) {
        if (recordedClassLoader == null) {
            return false;
        }
        if (this.id != null && this.id != recordedClassLoader.getId()) {
            return false;
        }
        if (this.type != null && !this.type.test(recordedClassLoader.getType())) {
            return false;
        }
        if (this.name != null && !this.name.equalsIgnoreCase(recordedClassLoader.getName())) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
