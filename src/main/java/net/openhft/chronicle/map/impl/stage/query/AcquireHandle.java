/*
 * Copyright 2015 Higher Frequency Trading
 *
 *  http://www.higherfrequencytrading.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.openhft.chronicle.map.impl.stage.query;

import net.openhft.chronicle.core.io.Closeable;
import net.openhft.chronicle.hash.Data;
import net.openhft.chronicle.hash.impl.stage.hash.CheckOnEachPublicOperation;
import net.openhft.chronicle.hash.locks.InterProcessLock;
import net.openhft.chronicle.map.MapEntry;
import net.openhft.chronicle.map.MapKeyContext;
import net.openhft.chronicle.map.impl.stage.entry.MapEntryStages;
import net.openhft.chronicle.map.impl.stage.ret.UsingReturnValue;
import net.openhft.lang.io.Bytes;
import net.openhft.sg.StageRef;
import net.openhft.sg.Staged;
import org.jetbrains.annotations.NotNull;

@Staged
public class AcquireHandle<K, V> implements Closeable {
    
    @StageRef CheckOnEachPublicOperation checkOnEachPublicOperation;
    @StageRef MapEntryStages<K, V> e;
    @StageRef MapQuery<K, V, ?> q;
    @StageRef UsingReturnValue<V> usingReturn;

    @Override
    public void close() {
        checkOnEachPublicOperation.checkOnEachPublicOperation();
        q.replaceValue(q.entry(), q.wrapValueAsData(usingReturn.returnValue()));
        q.close();
    }
}
