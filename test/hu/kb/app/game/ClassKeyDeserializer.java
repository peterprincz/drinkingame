package hu.kb.app.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

class ClassKeyDeserializer extends KeyDeserializer
{
    @Override
    public Object deserializeKey(final String key, final DeserializationContext ctxt ) throws IOException, JsonProcessingException
    {
        return null; // replace null with your logic
    }
}